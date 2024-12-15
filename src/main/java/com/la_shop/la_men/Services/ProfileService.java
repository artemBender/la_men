package com.la_shop.la_men.Services;


import com.la_shop.la_men.repo.ProfilesRepo;
import com.la_shop.la_men.Entities.Profiles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfilesRepo profilesRepo;

    public boolean isExist(Long user_id)
    {
        Long profileID = profilesRepo.GetProfileID(user_id);
        if (profileID != null)
            return profilesRepo.existsById(profileID);
        else return false;
    }

    public Profiles get(Long user_id)
    {
        Long profileID = profilesRepo.GetProfileID(user_id);
        return profilesRepo.findById(profileID).get();
    }

    public void update(String birthday, String town)
    {
        if (this.isExist(UserService.UserID))
        {
            profilesRepo.UpdateBirthday(birthday, UserService.UserID);
            profilesRepo.UpdateHometown(town, UserService.UserID);
        }
        else create(birthday, town);
    }

    public void create(String birthday, String town)
    {
        Profiles profile = new Profiles(UserService.UserID, birthday, town);
        profilesRepo.save(profile);
    }

}
