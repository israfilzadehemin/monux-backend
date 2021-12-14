create table translation
(
    id             bigint generated by default as identity
        primary key,
    az             varchar(255),
    en             varchar(255),
    ru             varchar(255),
    translation_id varchar(255)
);

alter table translation
    owner to postgres;

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

alter table rel_blog_with_translation_text
    owner to postgres;

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

alter table rel_blog_with_translation_title
    owner to postgres;

--banner relations
create table rel_banner_with_translation_text
(
    translation_id bigint
        constraint fkqh8dtfe29j36lan1cr34q8as0
            references translation,
    banner_id        bigint not null
        primary key
        constraint fkqkcki38ixx165qhbnq5ufm76t
            references banner
);

alter table rel_banner_with_translation_text
    owner to postgres;

create table rel_banner_with_translation_title
(
    translation_id bigint
        constraint fk658agjaeo8vx7lvvlq3p6oq51
            references translation,
    banner_id        bigint not null
        primary key
        constraint fkmv7gv5kujkfi1cb0r7lo67gbn
            references banner
);

alter table rel_banner_with_translation_title
    owner to postgres;