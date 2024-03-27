create database recipebook_schema;

# to give more space to recipe method attribute.. it was too small for multiple steps
alter table recipebook_schema.recipe modify method varbinary(3000);

alter table recipebook_schema.recipe modify notes varbinary(3000);
    
# need to modify constraints on all tables that use foreign keys to cascade on delete and on update. This allows us to delete and update a recipe.

######### recipe_dishes_that_also_use_leftover_ingredients
-- alter table recipebook_schema.recipe_dishes_that_also_use_leftover_ingredients
-- 	drop constraint `FK63d542lg8jdfjjxgu0at7gdit`;
--     
-- alter table recipebook_schema.recipe_dishes_that_also_use_leftover_ingredients
-- 	add constraint `FK63d542lg8jdfjjxgu0at7gdit`
--     FOREIGN KEY (`dishes_that_also_use_leftover_ingredients_id`) 
--     REFERENCES `recipe_name` (`id`)
-- 	on delete cascade on update cascade;
--     
-- alter table recipebook_schema.recipe_dishes_that_also_use_leftover_ingredients
-- 	drop constraint `FKe6gl9rsm9kyncrh56a1g6p2pe`;
--     
-- alter table recipebook_schema.recipe_dishes_that_also_use_leftover_ingredients
-- 	add constraint `FKe6gl9rsm9kyncrh56a1g6p2pe`
--     FOREIGN KEY (`recipe_id`) 
--     REFERENCES `recipe` (`id`)
-- 	on delete cascade on update cascade;

######### recipe_equipment
-- alter table recipebook_schema.recipe_equipment
-- 	drop constraint `FKboq5n31brc23q09cfnff0d0by`;
--     
-- alter table recipebook_schema.recipe_equipment
-- 	add constraint `FKboq5n31brc23q09cfnff0d0by`
--     FOREIGN KEY (`equipment_name`) 
--     REFERENCES `equipment` (`name`)
-- 	on delete cascade on update cascade;
--     
-- alter table recipebook_schema.recipe_equipment
-- 	drop constraint `FKjmrv4gvbggwmc5oq4bvhnjjsv`;
--     
-- alter table recipebook_schema.recipe_equipment
-- 	add constraint `FKjmrv4gvbggwmc5oq4bvhnjjsv`
--     FOREIGN KEY (`recipe_id`) 
--     REFERENCES `recipe` (`id`)
-- 	on delete cascade on update cascade;

######### recipe_ingredients
-- alter table recipebook_schema.recipe_ingredients
-- 	drop constraint `FKeoshvu1007ydvg1sbqxo986fe`;
--     
-- alter table recipebook_schema.recipe_ingredients
-- 	add constraint `FKeoshvu1007ydvg1sbqxo986fe`
--     FOREIGN KEY (`ingredients_key`) 
--     REFERENCES `ingredient` (`ingredient_name`)
-- 	on delete cascade on update cascade;
--     
-- alter table recipebook_schema.recipe_ingredients
-- 	drop constraint `FKhnsmvxdlwxqq6x2wbgnoef5gr`;
--     
-- alter table recipebook_schema.recipe_ingredients
-- 	add constraint `FKhnsmvxdlwxqq6x2wbgnoef5gr`
--     FOREIGN KEY (`recipe_id`) 
--     REFERENCES `recipe` (`id`)
-- 	on delete cascade on update cascade;

######### recipe_meal_affinities
-- alter table recipebook_schema.recipe_meal_affinities
-- 	drop constraint `FK9egtbc8qcc6h0wgwi2fwbnnda`;
--     
-- alter table recipebook_schema.recipe_meal_affinities
-- 	add constraint `FK9egtbc8qcc6h0wgwi2fwbnnda`
--     FOREIGN KEY (`recipe_id`) 
--     REFERENCES `recipe` (`id`)
-- 	on delete cascade on update cascade;
--     
-- alter table recipebook_schema.recipe_meal_affinities
-- 	drop constraint `FKjk350rspjxrtnfsh41gxhbkrs`;
--     
-- alter table recipebook_schema.recipe_meal_affinities
-- 	add constraint `FKjk350rspjxrtnfsh41gxhbkrs`
--     FOREIGN KEY (`meal_affinities_id`) 
--     REFERENCES `recipe_name` (`id`)
-- 	on delete cascade on update cascade;

######### recipe_often_made_alongside
-- alter table recipebook_schema.recipe_often_made_alongside
-- 	drop constraint `FKgw9wogu5nry0y5luxoydg19lu`;
--     
-- alter table recipebook_schema.recipe_often_made_alongside
--     add constraint `FKgw9wogu5nry0y5luxoydg19lu`
--     FOREIGN KEY (`often_made_alongside_id`) 
--     REFERENCES `recipe_name` (`id`)
-- 	on delete cascade on update cascade;

-- alter table recipebook_schema.recipe_often_made_alongside
-- 	drop constraint `FK39xx7kys1o1j6p11lj6d6lkov`;
--     
-- alter table recipebook_schema.recipe_often_made_alongside
--     add constraint `FK39xx7kys1o1j6p11lj6d6lkov`
--     FOREIGN KEY (`recipe_id`) 
--     REFERENCES `recipe` (`id`)
-- 	on delete cascade on update cascade;

######### recipe_pairings
-- alter table recipebook_schema.recipe_pairings
-- 	drop constraint `FKdajm344q7c8n6ihn049k5q4e6`;
--     
-- alter table recipebook_schema.recipe_pairings
-- 	add constraint `FKdajm344q7c8n6ihn049k5q4e6`
--     FOREIGN KEY (`pairings_id`) 
--     REFERENCES `recipe_name` (`id`)
-- 	on delete cascade on update cascade;
--     
-- alter table recipebook_schema.recipe_pairings
-- 	drop constraint `FKp5tc5np3nk5n0hrt3ax00magq`;
--     
-- alter table recipebook_schema.recipe_pairings
-- 	add constraint `FKp5tc5np3nk5n0hrt3ax00magq`
--     FOREIGN KEY (`recipe_id`) 
--     REFERENCES `recipe` (`id`)
-- 	on delete cascade on update cascade;

######### recipe_pairs_with
-- alter table recipebook_schema.recipe_pairs_with
-- 	drop constraint `FKfbhmg6lj1fecq8ek0rg1apqka`;
--     
-- alter table recipebook_schema.recipe_pairs_with
-- 	add constraint `FKfbhmg6lj1fecq8ek0rg1apqka`
--     FOREIGN KEY (`recipe_id`) 
--     REFERENCES `recipe` (`id`)
-- 	on delete cascade on update cascade;
--     
-- alter table recipebook_schema.recipe_pairs_with
-- 	drop constraint `FKt7psp8tl1cmkmdkypjx0iovf3`;
--     
-- alter table recipebook_schema.recipe_pairs_with
-- 	add constraint `FKt7psp8tl1cmkmdkypjx0iovf3`
--     FOREIGN KEY (`pairs_with_id`) 
--     REFERENCES `recipe_name` (`id`)
-- 	on delete cascade on update cascade;

######### planned_meal_menu
-- alter table recipebook_schema.planned_meal_menu
-- 	drop constraint `FKgb7uwj6rfvo7uq3djlsavynjm`;
--     
-- alter table recipebook_schema.planned_meal_menu
-- 	add constraint `FKgb7uwj6rfvo7uq3djlsavynjm`
--     FOREIGN KEY (`planned_meal_id`) 
--     REFERENCES `planned_meal` (`id`)
-- 	on delete cascade on update cascade;
--     
-- alter table recipebook_schema.planned_meal_menu
-- 	drop constraint `FKkoksq33goxjvixbo8h6lrlwty`;
--     
-- alter table recipebook_schema.planned_meal_menu
-- 	add constraint `FKkoksq33goxjvixbo8h6lrlwty`
--     FOREIGN KEY (`menu_id`) 
--     REFERENCES `recipe_name` (`id`)
-- 	on delete cascade on update cascade;

######### schedule_actual_meals
-- alter table recipebook_schema.schedule_actual_meals
-- 	drop constraint `FKbsp2tqe7xumk4c9g49qejufi3`;
--     
-- alter table recipebook_schema.schedule_actual_meals
-- 	add constraint `FKbsp2tqe7xumk4c9g49qejufi3`
--     FOREIGN KEY (`schedule_date`) 
--     REFERENCES `schedule` (`date`)
-- 	on delete cascade on update cascade;
--     
-- alter table recipebook_schema.schedule_actual_meals
-- 	drop constraint `FKc5o3bq207035gr5l61xvtyxlc`;
--     
-- alter table recipebook_schema.schedule_actual_meals
-- 	add constraint `FKc5o3bq207035gr5l61xvtyxlc`
--     FOREIGN KEY (`actual_meals_id`) 
--     REFERENCES `planned_meal` (`id`)
-- 	on delete cascade on update cascade;

######### schedule_all_meals_planned
-- alter table recipebook_schema.schedule_all_meals_planned
-- 	drop constraint `FK69dms9tj697gj0a3n8qjpd7la`;
--     
-- alter table recipebook_schema.schedule_all_meals_planned
-- 	add constraint `FK69dms9tj697gj0a3n8qjpd7la`
--     FOREIGN KEY (`schedule_date`) 
--     REFERENCES `schedule` (`date`)
-- 	on delete cascade on update cascade;
--     
-- alter table recipebook_schema.schedule_all_meals_planned
-- 	drop constraint `FKkuxtcppvpwmguv40j5a4lt6mi`;
--     
-- alter table recipebook_schema.schedule_all_meals_planned
-- 	add constraint `FKkuxtcppvpwmguv40j5a4lt6mi`
--     FOREIGN KEY (`all_meals_planned_id`) 
--     REFERENCES `planned_meal` (`id`)
-- 	on delete cascade on update cascade;

######### schedule_todays_planned_meals
-- alter table recipebook_schema.schedule_todays_planned_meals
-- 	drop constraint `FK5pnd91ic1p50dxmjqhh8gxrxj`;
--     
-- alter table recipebook_schema.schedule_todays_planned_meals
-- 	add constraint `FK5pnd91ic1p50dxmjqhh8gxrxj`
--     FOREIGN KEY (`schedule_date`) 
--     REFERENCES `schedule` (`date`)
-- 	on delete cascade on update cascade;
--     
-- alter table recipebook_schema.schedule_todays_planned_meals
-- 	drop constraint `FKl4ulwdee1fg2e3yewx3gaeb2w`;
--     
-- alter table recipebook_schema.schedule_todays_planned_meals
-- 	add constraint `FKl4ulwdee1fg2e3yewx3gaeb2w`
--     FOREIGN KEY (`todays_planned_meals_id`) 
--     REFERENCES `planned_meal` (`id`)
-- 	on delete cascade on update cascade;

######### schedule_tomorrows_planned_meals
-- alter table recipebook_schema.schedule_tomorrows_planned_meals
-- 	drop constraint `FKpgqppoxi9e7xgpqd8m4ah5k05`;
--     
-- alter table recipebook_schema.schedule_tomorrows_planned_meals
-- 	add constraint `FKpgqppoxi9e7xgpqd8m4ah5k05`
--     FOREIGN KEY (`schedule_date`) 
--     REFERENCES `schedule` (`date`)
-- 	on delete cascade on update cascade;
--     
-- alter table recipebook_schema.schedule_tomorrows_planned_meals
-- 	drop constraint `FKtrjh9lyfb5x695nlluoeh78x5`;
--     
-- alter table recipebook_schema.schedule_tomorrows_planned_meals
-- 	add constraint `FKtrjh9lyfb5x695nlluoeh78x5`
--     FOREIGN KEY (`tomorrows_planned_meals_id`) 
--     REFERENCES `planned_meal` (`id`)
-- 	on delete cascade on update cascade;






