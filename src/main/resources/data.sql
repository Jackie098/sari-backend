INSERT INTO sari_backend.users (id,created_at,updated_at,email,is_active,is_blocked,"name","password",phone,"role") VALUES
	 ('da918272-fde7-4558-99ad-c63f2c210841','2024-06-07 09:15:11.359872-03','2024-06-07 09:15:11.359872-03','adm@gmail.com',true,false,'Adminzão','$2a$12$u6eG7i8XNexFtKe4GKyVxuc.59uP3zxGrGFIsfAZvn8i9eiT8S8g2','89995138241','ADM'),
	 ('1801a1b4-fef8-43aa-92bd-dc7ddfaf895f','2024-06-07 09:20:18.503654-03','2024-06-07 10:29:25.617974-03','carlos@gmail.com',true,false,'carlos','$2a$12$tbNeiLSmz1lDdNcXSY1J8uSWlacmY9iovADjcfG8zuY0q1eG5Jra2','89995138240','ALUNO'),
	 ('ee0b0334-f8c0-444b-89b2-863187bac313','2024-06-11 08:09:33.109559-03','2024-06-11 08:09:33.109559-03','servidor@gmail.com',true,false,'servidor','$2a$12$a9Fkt0v5Ysgxqaowt8l9x.6KLseZ9VJ/MfR3IByXDhTtoCOa1l1WC','899951382401','SERVIDOR'),
	 ('4d37ec74-264f-440b-b4c9-0a437baf83bc','2024-06-12 13:48:24.477408-03','2024-06-12 13:48:24.477408-03','clara@gmail.com',true,false,'clara','$2a$12$WjNxaU3Mu.zIfhhvusdv5.uyMY.zHMaYXTg/.CByPf3KchP5IYF1a','89995138140','ALUNO'),
	 ('4d37ec74-264f-440b-b4c9-0a437baf83ac','2024-06-13 11:05:22.079197-03','2024-06-13 11:05:22.079197-03','blocked@gmail.com',false,false,'blocked','$2a$12$WjNxaU3Mu.zIfhhvusdv5.uyMY.zHMaYXTg/.CByPf3KchP5IYF1a','89995138147','ALUNO');


INSERT INTO sari_backend.ticket_meals (id,created_at,updated_at,amount_tickets,available_tickets,description,dessert,end_time,"name",start_time,status,"type",user_id) VALUES
	 ('7f325438-84a3-4b7c-baaa-ff25932a3f7f','2024-06-11 09:08:01.587554-03','2024-06-11 09:08:01.587554-03',150,150,'Muito molho bolhonesa, cheddar e ketchup','DOCE_DE_GOIABA','2024-06-11 13:30:00','Bife de porco','2024-06-11 11:00:00','SCHEDULED','ALMOCO','ee0b0334-f8c0-444b-89b2-863187bac313'),
	 ('cdf67ce7-66b2-4e49-80f9-d4fdcd925d39','2024-06-11 09:07:07.935965-03','2024-06-13 08:40:15.42386-03',150,150,'Muito molho bolhonesa, cheddar e ketchup','GELATINA','2024-06-13 08:30:00','Bife de porco','2024-06-13 08:30:00','FINISHED','ALMOCO','ee0b0334-f8c0-444b-89b2-863187bac313'),
	 ('e38796d9-29b4-44fa-bfac-335b5614c19b','2024-06-11 09:09:21.23628-03','2024-06-13 09:06:41.040906-03',150,150,'Muito molho bolhonesa, cheddar e ketchup','DOCE_DE_GOIABA','2024-06-13 09:15:00','Bife de porco','2024-06-13 09:05:00','AVAILABLE','JANTA','ee0b0334-f8c0-444b-89b2-863187bac313'),
	 ('2a2f9296-4311-41a8-a985-a04eab9d5c2e','2024-06-11 09:09:04.977638-03','2024-06-13 13:28:33.721347-03',150,145,'Muito molho bolhonesa, cheddar e ketchup','DOCE_DE_BANANA','2024-06-13 15:45:00','Bife de porco','2024-06-13 14:45:00','SCHEDULED','ALMOCO','1801a1b4-fef8-43aa-92bd-dc7ddfaf895f');

INSERT INTO sari_backend.book_meal (created_at,updated_at,status,ticket_meal_id,user_id) VALUES
	 ('2024-06-12 08:42:51.789442-03','2024-06-12 08:42:51.789442-03','BOOKED','cdf67ce7-66b2-4e49-80f9-d4fdcd925d39','1801a1b4-fef8-43aa-92bd-dc7ddfaf895f'),
	 ('2024-06-12 08:45:10.957096-03','2024-06-12 08:45:10.957096-03','BOOKED','7f325438-84a3-4b7c-baaa-ff25932a3f7f','1801a1b4-fef8-43aa-92bd-dc7ddfaf895f'),
	 ('2024-06-12 13:49:17.005881-03','2024-06-12 13:49:17.005881-03','BOOKED','7f325438-84a3-4b7c-baaa-ff25932a3f7f','4d37ec74-264f-440b-b4c9-0a437baf83bc'),
	 ('2024-06-12 13:52:22.570424-03','2024-06-13 10:51:12.972643-03','USED','e38796d9-29b4-44fa-bfac-335b5614c19b','4d37ec74-264f-440b-b4c9-0a437baf83bc'),
	 ('2024-06-13 13:10:15.973747-03','2024-06-13 13:28:33.721347-03','CANCELED','2a2f9296-4311-41a8-a985-a04eab9d5c2e','1801a1b4-fef8-43aa-92bd-dc7ddfaf895f');