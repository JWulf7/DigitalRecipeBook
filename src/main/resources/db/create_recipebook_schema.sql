create database recipebook_schema;

-- alter table recipebook_schema.recipe_often_made_alongside
-- 	drop constraint `FK39xx7kys1o1j6p11lj6d6lkov`;
--     
-- alter table recipebook_schema.recipe_often_made_alongside
--     add constraint `FK39xx7kys1o1j6p11lj6d6lkov`
--     FOREIGN KEY (`recipe_id`) 
--     REFERENCES `recipe` (`id`)
-- 	on delete cascade on update cascade;
    
# need to modify constraints on all tables that use foreign keys to cascade on delete and on update. This allows us to delete and update a recipe.

-- alter table recipebook_schema.recipe_often_made_alongside
-- 	drop constraint `FKgw9wogu5nry0y5luxoydg19lu`;
--     
-- alter table recipebook_schema.recipe_often_made_alongside
--     add constraint `FKgw9wogu5nry0y5luxoydg19lu`
--     FOREIGN KEY (`often_made_alongside_id`) 
--     REFERENCES `recipe_name` (`id`)
-- 	on delete cascade on update cascade;