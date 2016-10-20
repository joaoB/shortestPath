# Repo schema

# --- !Ups
create table `repo` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255) NOT NULL,
  unique(`name`)
);


# --- !Downs
drop table `repo`;