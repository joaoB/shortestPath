# Repo schema

# --- !Ups
create table `repo` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` TEXT NOT NULL
);


# --- !Downs
drop table `repo`;