INSERT INTO public."Cake"("Name", "Price", "CookingTime") VALUES('Butter', 300, 60000) ON CONFLICT DO NOTHING;
INSERT INTO public."Cake"("Name", "Price", "CookingTime") VALUES('Cookie', 300, 80000) ON CONFLICT DO NOTHING;
INSERT INTO public."Cake"("Name", "Price", "CookingTime") VALUES('Chiffon', 300, 90000) ON CONFLICT DO NOTHING;