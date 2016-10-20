# Contributor schema

# --- !Ups
create table `contributor` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username` varchar(255) NOT NULL,
  UNIQUE(`username`)
);

# --- !Downs
drop table `contributor`;

