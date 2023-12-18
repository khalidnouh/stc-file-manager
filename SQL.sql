Select usr.user_id, usr.username, dtl.training_id,
       dtl.training_date, count(dtl.training_id) as Counts
From User usr
Inner Join Training_details dtl ON dtl.user_id = usr.user_id
GRoup BY usr.user_id, usr.username, dtl.training_id,
         dtl.training_date
having count(dtl.training_id) > 1
Order BY dtl.training_date desc;