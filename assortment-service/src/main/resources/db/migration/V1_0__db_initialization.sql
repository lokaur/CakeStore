CREATE TABLE IF NOT EXISTS public."User"
(
    "Id" int NOT NULL GENERATED ALWAYS AS IDENTITY,
    "Name" character(50) NOT NULL,
    "Password" character(100) NOT NULL,
    PRIMARY KEY ("Id")
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public."User"
    OWNER to postgres;

INSERT INTO public."User"("Name", "Password") VALUES('admin', 'securepassword') ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS public."Cake"
(
    "Id" int NOT NULL GENERATED ALWAYS AS IDENTITY,
    "Name" character(50) NOT NULL,
    "Price" int NOT NULL,
    "CookingTime" int NOT NULL,
    PRIMARY KEY ("Id")
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public."Cake"
    OWNER to postgres;

INSERT INTO public."Cake"("Name", "Price", "CookingTime") VALUES('Prague', 500, 120000) ON CONFLICT DO NOTHING;