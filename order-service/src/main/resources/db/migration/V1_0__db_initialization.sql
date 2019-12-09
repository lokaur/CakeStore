CREATE TABLE IF NOT EXISTS public."OrderStatus"
(
    "Id" integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    "Name" character(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "OrderStatus_pkey" PRIMARY KEY ("Id")
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public."OrderStatus"
    OWNER to postgres;

INSERT INTO public."OrderStatus"("Name")
    VALUES  ('New'),
            ('InProgress'),
            ('Done');


CREATE TABLE IF NOT EXISTS public."Order"
(
    "Id" integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    "CakeId" integer NOT NULL,
    "StatusId" integer NOT NULL,
    "CreatedAt" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT "Order_pkey" PRIMARY KEY ("Id"),
    CONSTRAINT "FK_OrderStatus_Id" FOREIGN KEY ("StatusId")
        REFERENCES public."OrderStatus" ("Id") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public."Order"
    OWNER to postgres;