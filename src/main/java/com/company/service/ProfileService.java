package com.company.service;

import com.company.changeDto.ChangeEmailDTO;
import com.company.changeDto.ChangePswdDTO;
import com.company.changeDto.NameSurChangeDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.ProfileEmailJwtDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import com.company.exception.EmailAlreadyExistsException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private EmailService emailService;

    /** CREATE profile ( admin ) */
    public ProfileDTO create(ProfileDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            log.warn("email alredy exists : {}", dto );
            throw new EmailAlreadyExistsException("Email Already Exits");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    /** GET BY ID  */
    public ProfileDTO getById(Integer id) {
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not Found!"));
        return toDTO(entity);
    }

    /** UPDATE profile ( name , surname ) */
    public NameSurChangeDTO update(Integer pId, NameSurChangeDTO dto) {

        ProfileEntity entity = profileRepository.findById(pId).orElseThrow(() -> {
            log.warn("id not found : {}", pId );
           throw  new ItemNotFoundException("Not Found!");
        });

        if (entity.getStatus().equals(ProfileStatus.DELETED)) {
            log.warn("id not found : {}", pId );
            throw new ItemNotFoundException("Not Found!");
        }

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUpdateDate(LocalDateTime.now());
        profileRepository.save(entity);

        return dto;
    }

    /** DELETE BY ID profile  */
    public Boolean delete(Integer id) {
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not Found!"));

        if (entity==null) {
            log.warn("id not found : {}", id );
            throw new ItemNotFoundException("Not Found!");
        }

        int n = profileRepository.updateStatus(ProfileStatus.DELETED, id);
        return n > 0;
    }

    /** UPDATE PHOTO profile  */
    public boolean updateImage(MultipartFile file, Integer pId) {
        ProfileEntity profileEntity = get(pId);

        if (profileEntity.getAttach() !=null ) {
            AttachEntity attachEntity1=attachService.updateForProfile(file,profileEntity.getAttach().getId());
            profileEntity.setAttach(attachEntity1);
        } else if (profileEntity.getAttach() == null) {
            AttachEntity attachEntity2=attachService.uploadForProfile(file);
            profileEntity.setAttach(attachEntity2);
        }

        profileRepository.save(profileEntity);

        return true;
    }

    /** DELETE profile  */
    public boolean deleteImage(Integer pId) {
        ProfileEntity profileEntity = get(pId);

        if (attachService.delete(profileEntity.getAttach().getId())){
            profileEntity.setAttach(null);
            return true;
        }
        return false;
    }

    /** CHANGE PASSWORD profile  */
    public String changePassword(ChangePswdDTO dto){
        Optional<ProfileEntity> optional= Optional.ofNullable(profileRepository.findByEmailAndPassword(dto.getEmail(), dto.getOldPassword())
                .orElseThrow(() -> {
                    throw new ItemNotFoundException("Email or old Password wrong");
                }));
        ProfileEntity entity=optional.get();
        entity.setPassword(dto.getNewPassword());
        entity.setUpdateDate(LocalDateTime.now());

        profileRepository.save(entity);
        return "Your new password: "+dto.getNewPassword();
    }

    /** CHANGE EMAIL profile  */
    public String changeEmail(ChangeEmailDTO dto, Integer pId){
        profileRepository.findById(pId)
                .orElseThrow(()->{throw new ItemNotFoundException("Item not found");});
        Thread thread = new Thread() {
            @Override
            public void run() {
                sendVerificationEmail(dto, pId);
            }
        };
        thread.start();
        return "Send email link ";
    }

    /** CHANGE EMAIL VERIFICATION profile  */
    public  String changeEmailVerification(String jwt){
        ProfileEmailJwtDTO dto =JwtUtil.decodeEmail(jwt);

        ProfileEntity entity=profileRepository.findById(dto.getId()).get();

        entity.setEmail(dto.getEmail());

        profileRepository.save(entity);

        return "change email: "+dto.getEmail();
    }







    /**
     *  auxiliary methods
     */

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setCreateDate(entity.getCreateDate());
        if (entity.getAttach()!=null){
            dto.setPhotoURL(attachService.getPhotoURL(entity.getAttach()));
        }
        return dto;
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not Found!"));
    }

    //send email verification link
    private void sendVerificationEmail(ChangeEmailDTO dto, Integer pId) {
        StringBuilder builder = new StringBuilder();
        String jwt = JwtUtil.encodeEmail(pId,dto.getNewEmail());
        builder.append("Salom bormsin \n");
        builder.append("To verify your registration click to next link.");
        builder.append("http://localhost:8080/profile/changeEmailVer/").append(jwt);
        builder.append("\nMazgi!");
        emailService.send(dto.getNewEmail(), "Activate Your Registration", builder.toString());

    }
}
