create table translation
(
    id             bigint generated by default as identity
        primary key,
    az             varchar(255),
    en             varchar(255),
    ru             varchar(255),
    translation_id varchar(255)
);

--blog relations
create table rel_blog_with_translation_text
(
    translation_id bigint
        constraint fkqh8dtfe29j36lan1cr34q8as0
            references translation,
    blog_id        bigint not null
        primary key
        constraint fkqkcki38ixx165qhbnq5ufm76t
            references blog
);

create table rel_blog_with_translation_title
(
    translation_id bigint
        constraint fk658agjaeo8vx7lvvlq3p6oq51
            references translation,
    blog_id        bigint not null
        primary key
        constraint fkmv7gv5kujkfi1cb0r7lo67gbn
            references blog
);

--banner relations
create table rel_banner_with_translation_text
(
    translation_id bigint
        constraint fkqh8dtfe29j36lan1cr34q8as0
            references translation,
    banner_id      bigint not null
        primary key
        constraint fkqkcki38ixx165qhbnq5ufm76t
            references banner
);

create table rel_banner_with_translation_title
(
    translation_id bigint
        constraint fk658agjaeo8vx7lvvlq3p6oq51
            references translation,
    banner_id      bigint not null
        primary key
        constraint fkmv7gv5kujkfi1cb0r7lo67gbn
            references banner
);

create table rel_definition_with_translation_text
(
    translation_id bigint
        constraint fkptk8y9j3cgkgx74itegj5tltm
            references translation,
    definition_id  bigint not null
        primary key
        constraint fkh4nn1ij4uk8qyc2dqgnpolf3j
            references definition
);

create table rel_definition_with_translation_title
(
    translation_id bigint
        constraint fkc5trcnw7kr8nshm9xxv4ftwg6
            references translation,
    definition_id  bigint not null
        primary key
        constraint fk2es1r968vnpwno4bopqv4nqvj
            references definition
);

create table rel_faq_with_translation_answer
(
    translation_id bigint
        constraint fk977qxc0ue1iyyxc16mawhidu4
            references translation,
    faq_id         bigint not null
        primary key
        constraint fk2biwqeja7u1pd1i9b2qgt7i5f
            references faq
);

create table rel_faq_with_translation_question
(
    translation_id bigint
        constraint fkqe2v3rm5i6niqp9kg08eqtfys
            references translation,
    faq_id         bigint not null
        primary key
        constraint fk93910hn81k76mqumgd2ts1iix
            references faq
);

create table rel_feature_with_translation
(
    translation_id bigint
        constraint fkeogqoafbwuq18omh15t6oykvx
            references translation,
    feature_id     bigint not null
        primary key
        constraint fk97dljvsyxn3jjgwdi1t1qqlp9
            references feature
);

create table rel_plan_with_translation_text
(
    translation_id bigint
        constraint fkbb9kv2vvggfhaljf1ingemat7
            references translation,
    plan_id        bigint not null
        primary key
        constraint fkmwwepm2c1sv320rdyvsm9k4n1
            references plan
);

create table rel_plan_with_translation_title
(
    translation_id bigint
        constraint fkle8myiwiv9ar64erb1hfxbkhd
            references translation,
    plan_id        bigint not null
        primary key
        constraint fkmr0xegueg5k5tb1c4xylkb2k5
            references plan
);

create table rel_service_with_translation_text
(
    translation_id bigint
        constraint fkfmpoxavyhdks0litahslp3w0k
            references translation,
    service_id     bigint not null
        primary key
        constraint fkh1r12u2cjqdwdi9yhxxyegmk9
            references service
);

create table rel_service_with_translation_title
(
    translation_id bigint
        constraint fkoo1fwk6jwj01opcx3thhwapf
            references translation,
    service_id     bigint not null
        primary key
        constraint fkge4fx28fjjb6gsvxh2podjv85
            references service
);

create table rel_step_with_translation_text
(
    translation_id bigint
        constraint fkagt0cs4fjubkh253whbin9r5y
            references translation,
    step_id        bigint not null
        primary key
        constraint fkahkjbs2xj9vd4bi4hnm59hbgw
            references step
);

create table rel_step_with_translation_title
(
    translation_id bigint
        constraint fkny9l42cpquwq3etlhfdvejm2u
            references translation,
    step_id        bigint not null
        primary key
        constraint fkp9kr4ap7pw2klm066htoinp3
            references step
);
