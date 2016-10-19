# user had repo schema

# --- !Ups
CREATE TABLE `contributor_has_repo`
(
    contributorId      INTEGER NOT NULL REFERENCES contributor,
    repoId   INTEGER NOT NULL REFERENCES repo,
    PRIMARY KEY(contributorId, repoId)
); 


# --- !Downs
drop table `contributor_has_repo`;