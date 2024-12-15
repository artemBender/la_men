package com.la_shop.la_men.Services;

import com.la_shop.la_men.Entities.Feedback;
import com.la_shop.la_men.Entities.Moderation;
import com.la_shop.la_men.repo.FeedbackRepo;
import com.la_shop.la_men.repo.ModerationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepo feedbackRepo;
    private final ModerationRepo moderationRepo;

    public List<Feedback> getComments (Long prod_id)
    {
        return feedbackRepo.GetFeedbackByProductID(prod_id);
    }

    public void create (Long user_id, Long product_id, String feedback)
    {
        Moderation user_feedback = new Moderation(user_id, product_id, feedback);
        moderationRepo.save(user_feedback);
    }

    public List<Moderation> get()
    {
        return (List<Moderation>) moderationRepo.findAll();
    }

    public Moderation get(Long moderation_id)
    {
        return moderationRepo.GetFeedback(moderation_id);
    }

    public void delete(Long moderation_id)
    {
        moderationRepo.DeleteFeedback(moderation_id);
    }

    public void create (Long moderation_id)
    {
        Moderation moderation = this.get(moderation_id);
        Feedback user_feedback = new Feedback(moderation.getUser_id(), moderation.getProduct_id(), moderation.getFeedback_body());
        feedbackRepo.save(user_feedback);
    }


    public void moderate(String action, Long moderation_id)
    {
        switch (action)
        {
            case "delete":
                this.delete(moderation_id);
                break;
            case "public":
            {
                this.create(moderation_id);
                this.delete(moderation_id);
            }
            break;
        }
    }

}
