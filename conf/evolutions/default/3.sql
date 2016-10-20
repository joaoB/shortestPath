# user had repo schema

# --- !Ups
CREATE TABLE `contributor_has_repo`
(
    contributorId      varchar(255) NOT NULL REFERENCES contributor,
    repoId   varchar(255) NOT NULL REFERENCES repo,
    PRIMARY KEY(contributorId, repoId)
); 


# --- !Downs
drop table `contributor_has_repo`;