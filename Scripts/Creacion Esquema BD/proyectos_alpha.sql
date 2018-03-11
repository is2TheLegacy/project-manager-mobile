CREATE TABLE "usuario" (
"id_usuario" uuid NOT NULL,
"alias" varchar(30) NOT NULL,
"email" varchar(255) NOT NULL,
"nombre" varchar(100),
"apellido" varchar(100),
"sexo" varchar(1),
"fecha_creacion" timestamp NOT NULL,
"password_salt" varchar(8) NOT NULL,
"password" varchar(256) NOT NULL,
"estado" varchar(10) NOT NULL DEFAULT 'ACTIVO',
PRIMARY KEY ("id_usuario") ,
CONSTRAINT "uq_nombre_usuario" UNIQUE ("alias"),
CONSTRAINT "uq_email" UNIQUE ("email")
);

COMMENT ON COLUMN "usuario"."password_salt" IS 'random salt para concatenar al password';
COMMENT ON COLUMN "usuario"."password" IS 'SHA-256 del password';

CREATE TABLE "proyecto" (
"id_proyecto" uuid NOT NULL,
"propietario" uuid NOT NULL,
"nombre" varchar(100) NOT NULL,
"descripcion" varchar(2000) NOT NULL,
"categoria" int2,
"ur_imagen_portada" varchar(2083),
"fecha_creacion" timestamp NOT NULL,
"fecha_finalizacion" timestamp,
"estado" varchar(10) NOT NULL DEFAULT 'ACTIVO',
PRIMARY KEY ("id_proyecto") 
);

COMMENT ON COLUMN "proyecto"."propietario" IS 'El el usuario dueño del proyecto';
COMMENT ON COLUMN "proyecto"."ur_imagen_portada" IS 'URL de la imagen de portada.';

CREATE TABLE "miembro_proyecto" (
"proyecto" uuid NOT NULL,
"usuario" uuid NOT NULL,
"rol" int2 NOT NULL,
"fecha_membresia" timestamp NOT NULL,
"estado" varchar(10) NOT NULL DEFAULT 'ACTIVO',
PRIMARY KEY ("proyecto", "usuario") 
);

COMMENT ON COLUMN "miembro_proyecto"."rol" IS 'Rol que cumple el usuario en el proyecto';
COMMENT ON COLUMN "miembro_proyecto"."fecha_membresia" IS 'La fecha en que se activó la membresia';
COMMENT ON COLUMN "miembro_proyecto"."estado" IS 'Estado de la membresia';

CREATE TABLE "rol_proyecto" (
"id_rol_proyecto" serial2 NOT NULL,
"nombre" varchar(20) NOT NULL,
PRIMARY KEY ("id_rol_proyecto") ,
CONSTRAINT "uq_nombre_rol_proyecto" UNIQUE ("nombre")
);

COMMENT ON COLUMN "rol_proyecto"."nombre" IS 'Nombre del rol: Administrador, Colaborador, Seguidor, etc...';

CREATE TABLE "categoria_proyecto" (
"id_categoria_proyecto" serial2 NOT NULL,
"nombre" varchar(100) NOT NULL,
PRIMARY KEY ("id_categoria_proyecto") ,
CONSTRAINT "uq_nombre" UNIQUE ("nombre")
);

CREATE TABLE "solicitud_colaboracion" (
"id_solicitud_colaboracion" uuid NOT NULL,
"proyecto" uuid NOT NULL,
"usuario_origen" uuid NOT NULL,
"usuario_destino" uuid NOT NULL,
"fecha_solitud" timestamp NOT NULL,
"estado" varchar(10) NOT NULL DEFAULT 'PENDIENTE',
"mensaje" varchar(2000) NOT NULL,
PRIMARY KEY ("id_solicitud_colaboracion") 
);

COMMENT ON COLUMN "solicitud_colaboracion"."estado" IS 'El estado de la solicitud; Pendiente, Aceptada, Rechazada';

CREATE TABLE "hito" (
"id_hito" uuid NOT NULL,
"nombre" varchar(100) NOT NULL,
"descripcion" varchar(500),
"proyecto" uuid NOT NULL,
"fecha_inicio" date NOT NULL,
"fecha_estimada_fin" date NOT NULL,
"fecha_real_fin" date,
"usuario_creador" uuid NOT NULL,
PRIMARY KEY ("id_hito") 
);

CREATE TABLE "tarea" (
"id_tarea" uuid NOT NULL,
"nombre" varchar(200) NOT NULL,
"descripcion" varchar(2000) NOT NULL,
"usuario_creador" uuid NOT NULL,
"fecha_creacion" timestamp NOT NULL,
"usuario_asignado" uuid,
"porcentaje_realizado" int2 NOT NULL DEFAULT 0,
"prioridad" varchar(10) NOT NULL DEFAULT 'NORMAL',
"estado" varchar(10) NOT NULL DEFAULT 'ABIERTA',
"fecha_fin" date,
"fecha_inicio" date,
"fecha_estimada_fin" date,
"fecha_estimada_inicio" date,
PRIMARY KEY ("id_tarea") ,
CONSTRAINT "uq_nombre_tarea" UNIQUE ("nombre")
);

COMMENT ON COLUMN "tarea"."prioridad" IS 'Pueden ser, NORMAL, BAJA, MEDIA, ALTA, MUY ALTA';
COMMENT ON COLUMN "tarea"."estado" IS 'Pueden ser ABIERTA, CERRADA, PENDIENTE, EN CURSO, FINALIZADA';

CREATE TABLE "comentario" (
"id_comentario" uuid NOT NULL,
"usuario_creador" uuid NOT NULL,
"fecha_creacion" timestamp NOT NULL,
"contenido" varchar(1000) NOT NULL,
"tarea" uuid NOT NULL,
PRIMARY KEY ("id_comentario") 
);


ALTER TABLE "miembro_proyecto" ADD CONSTRAINT "fk_miembro_proyecto_rol_proyecto_1" FOREIGN KEY ("rol") REFERENCES "rol_proyecto" ("id_rol_proyecto");
COMMENT ON CONSTRAINT "fk_miembro_proyecto_rol_proyecto_1" ON "miembro_proyecto" IS 'Un usuario miembro cumple un solo rol dentro del proyecto';
ALTER TABLE "proyecto" ADD CONSTRAINT "fk_proyecto_usuario_1" FOREIGN KEY ("propietario") REFERENCES "usuario" ("id_usuario");
ALTER TABLE "miembro_proyecto" ADD CONSTRAINT "fk_miembro_proyecto_usuario_1" FOREIGN KEY ("usuario") REFERENCES "usuario" ("id_usuario");
COMMENT ON CONSTRAINT "fk_miembro_proyecto_usuario_1" ON "miembro_proyecto" IS 'Un usuario es miembro de 1 o N proyectos';
ALTER TABLE "proyecto" ADD CONSTRAINT "fk_proyecto_categoria_proyecto_1" FOREIGN KEY ("categoria") REFERENCES "categoria_proyecto" ("id_categoria_proyecto");
ALTER TABLE "solicitud_colaboracion" ADD CONSTRAINT "fk_solicitud_colaboracion_proyecto_1" FOREIGN KEY ("proyecto") REFERENCES "proyecto" ("id_proyecto");
ALTER TABLE "solicitud_colaboracion" ADD CONSTRAINT "fk_solicitud_colaboracion_usuario_1" FOREIGN KEY ("usuario_origen") REFERENCES "usuario" ("id_usuario");
ALTER TABLE "solicitud_colaboracion" ADD CONSTRAINT "fk_solicitud_colaboracion_usuario_2" FOREIGN KEY ("usuario_destino") REFERENCES "usuario" ("id_usuario");
ALTER TABLE "hito" ADD CONSTRAINT "fk_hito_usuario_1" FOREIGN KEY ("usuario_creador") REFERENCES "usuario" ("id_usuario");
ALTER TABLE "hito" ADD CONSTRAINT "fk_hito_proyecto_1" FOREIGN KEY ("proyecto") REFERENCES "proyecto" ("id_proyecto");
ALTER TABLE "tarea" ADD CONSTRAINT "fk_tarea_usuario_1" FOREIGN KEY ("usuario_creador") REFERENCES "usuario" ("id_usuario");
ALTER TABLE "tarea" ADD CONSTRAINT "fk_tarea_usuario_2" FOREIGN KEY ("usuario_asignado") REFERENCES "usuario" ("id_usuario");
ALTER TABLE "comentario" ADD CONSTRAINT "fk_comentario_tarea_1" FOREIGN KEY ("tarea") REFERENCES "tarea" ("id_tarea");
ALTER TABLE "comentario" ADD CONSTRAINT "fk_comentario_usuario_1" FOREIGN KEY ("usuario_creador") REFERENCES "usuario" ("id_usuario");
ALTER TABLE "miembro_proyecto" ADD CONSTRAINT "fk_miembro_proyecto_proyecto_1" FOREIGN KEY ("proyecto") REFERENCES "proyecto" ("id_proyecto");

