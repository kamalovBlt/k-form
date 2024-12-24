CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table users
(
    id       uuid         not null default uuid_generate_v4(),
    name     varchar(100) not null,
    surname  varchar(100) not null,
    email    varchar(100) not null,
    password text         not null,
    ----------------------------------------------------------
    constraint users_id_pk primary key (id),
    constraint users_email_uq unique (email)
);

comment on table users is 'Таблица пользователей';
comment on column users.id is 'ID пользователя';
comment on column users.name is 'Имя пользователя';
comment on column users.surname is 'Фамилия пользователя';
comment on column users.email is 'Почта пользователя';
comment on column users.password is 'Хэш пароля пользователя';

create table test
(
    id          uuid         not null default uuid_generate_v4(),
    user_id     uuid         not null,
    title       varchar(255) not null,
    description TEXT,
    category    varchar(100) not null,
    created_at  timestamp             default current_timestamp,
    max_ball    integer      not null,

    -------------------------------------------------------------
    constraint test_user_id_fk foreign key (user_id) references users (id),
    constraint test_id_pk primary key (id)
);

comment on table test is 'Таблица тестов';
comment on column test.id is 'ID теста';
comment on column test.user_id is 'ID пользователя, создавшего тест';
comment on column test.title is 'Название теста';
comment on column test.description is 'Описание теста';
comment on column test.created_at is 'Дата создания теста';

create table question
(
    id      uuid not null default uuid_generate_v4(),
    test_id uuid not null,
    number  int  not null,
    text    text not null,
    score   int  not null,
    ------------------------------------------------------------------------------
    constraint question_ref_test_id_fk foreign key (test_id) references test (id),
    constraint question_id_pk primary key (id)
);

comment on table question is 'Таблица вопросов';
comment on column question.id is 'ID вопроса';
comment on column question.test_id is 'ID теста к которому принадлежит вопрос';
comment on column question.number is 'Номер вопроса';
comment on column question.text is 'Текст вопроса';
comment on column question.score is 'Балл за вопрос';

create table answer
(
    id          uuid not null default uuid_generate_v4(),
    question_id uuid not null,
    text        text not null,
    is_correct  boolean       default false,
    ------------------------------------------------------------------------------------
    constraint answer_id_pk primary key (id),
    constraint answer_ref_question_id foreign key (question_id) references question (id)
);

comment on table answer is 'Таблица ответов';
comment on column answer.id is 'ID ответа';
comment on column answer.question_id is 'ID вопроса к которому принадлежит ответ';
comment on column answer.text is 'Текст ответа';
comment on column answer.is_correct is 'Правильность ответа';

create table users_completed_tests
(
    user_id      uuid      not null,
    test_id      uuid      not null,
    completed_at timestamp not null default current_timestamp,
    result       integer   not null,
    --------------------------------------------------------------------------------------
    constraint users_completed_tests_pk primary key (user_id, test_id, completed_at),
    constraint users_completed_tests_user_id_fk foreign key (user_id) references users (id),
    constraint users_completed_tests_test_id_fk foreign key (test_id) references test (id)
);

comment on table users_completed_tests is 'Таблица пройденных тестов пользователей';
comment on column users_completed_tests.user_id is 'ID пользователя';
comment on column users_completed_tests.test_id is 'ID теста';
comment on column users_completed_tests.completed_at is 'Дата прохождения теста';

create table test_result
(
    id          uuid    not null default uuid_generate_v4(),
    test_id     uuid    not null,
    min_score   integer not null,
    max_score   integer not null,
    description text    not null,
    -------------------------------------------------------------
    constraint test_results_id_pk primary key (id),
    constraint test_results_test_id_fk foreign key (test_id) references test (id) on delete cascade
);

comment on table test_result is 'Таблица описаний результатов тестов';
comment on column test_result.id is 'ID записи';
comment on column test_result.test_id is 'ID теста';
comment on column test_result.min_score is 'Минимальный балл для диапазона';
comment on column test_result.max_score is 'Максимальный балл для диапазона';
comment on column test_result.description is 'Описание для данного диапазона баллов';

