# Contributor schema

# --- !Ups
create table `contributor` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username` TEXT NOT NULL
);

# --- !Downs
drop table `contributor`;

