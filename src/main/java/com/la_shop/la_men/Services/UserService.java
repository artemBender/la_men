package com.la_shop.la_men.Services;

import com.la_shop.la_men.Entities.Basket;
import com.la_shop.la_men.Entities.Profiles;
import com.la_shop.la_men.Entities.Storehouse;
import com.la_shop.la_men.Entities.Users;
import com.la_shop.la_men.repo.ProfilesRepo;
import com.la_shop.la_men.repo.UsersRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String SESSION_ID_KEY = "sessionId";
    private static final String ADMIN_EMAIL = "special_email@uuu.com";
    private static final String ADMIN_PASSWORD = "adminadmin";
    public static final Long ADMIN_ID = 90L;

    public static Double BALANCE;

    public static Long UserID;


    private final UsersRepo usersRepo;
    private final ProfilesRepo profilesRepo;
    private final BasketService basketService;

    public static void setUser (Model model)
    {
        if (Objects.equals(UserID, ADMIN_ID))
            model.addAttribute("admin", 1);
        if(UserID == null)
            model.addAttribute("user", 0);
        else model.addAttribute("user", 1);
    }

    public void setBalance()
    {
        if(UserID != null)
            BALANCE = usersRepo.GetUserByID(UserID).getRansom();
    }

    public void updateBalance(Double balance, Long user_id)
    {
        usersRepo.UpdateBalance(balance, user_id);
    }
    public int isExist (String email)
    {
        return usersRepo.isExist(email);
    }

    public void registerUser (HttpServletRequest request, String firstname, String lastname, String email, String phone, String password)
    {
        Users user = new Users(firstname, lastname, email, phone, BCrypt.hashpw(password, BCrypt.gensalt()), 0d);
        usersRepo.save(user);
        String userId = String.valueOf(usersRepo.GetUsersID(email,BCrypt.hashpw(password, BCrypt.gensalt())));
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_ID_KEY, userId);
        MDC.put(SESSION_ID_KEY, userId);
        UserID = Long.valueOf(userId);
        Profiles profiles = new Profiles(UserID, null, null);
        profilesRepo.save(profiles);
    }

    public String login(String email, String password, HttpServletRequest request)
    {
        if(BCrypt.checkpw(password, usersRepo.getPasswordUserByEmail(email)))
        {
            String userId = String.valueOf(usersRepo.GetUsersID(email,usersRepo.getPasswordUserByEmail(email)));
            HttpSession session = request.getSession();
            session.setAttribute(SESSION_ID_KEY, userId);
            MDC.put(SESSION_ID_KEY, userId);
            UserID = Long.valueOf(userId);
            return "redirect:/";
        }
        else {
            return "login";
        }
    }

    public void logout(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        MDC.remove(SESSION_ID_KEY);
        UserID = null;
        basketService.clean();
    }

    public Users get (Long user_id)
    {
        return usersRepo.GetUserByID(user_id);
    }

    public void update(String firstname, String lastname, String phone, String email)
    {
        usersRepo.UpdateFirstname(firstname, UserID);
        usersRepo.UpdateLastname(lastname, UserID);
        usersRepo.UpdatePhone(phone, UserID);
        usersRepo.UpdateEmail(email, UserID);
    }

    public void update(String new_password, String email)
    {
        usersRepo.UpdatePassword(BCrypt.hashpw(new_password, BCrypt.gensalt()), email);
    }


}
