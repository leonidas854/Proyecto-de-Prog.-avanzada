PGDMP  9    +                |         
   db_empresa    17.0    17.0 b    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            �           1262    41774 
   db_empresa    DATABASE     }   CREATE DATABASE db_empresa WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Spain.1252';
    DROP DATABASE db_empresa;
                     postgres    false            �            1259    41954    asignacion_ruta    TABLE     7  CREATE TABLE public.asignacion_ruta (
    id_asignacion bigint NOT NULL,
    detalles character varying(255) NOT NULL,
    estado character varying(255) NOT NULL,
    fechade_asignacion timestamp(6) without time zone NOT NULL,
    nombre_ruta character varying(255) NOT NULL,
    vehiculo_id bigint NOT NULL
);
 #   DROP TABLE public.asignacion_ruta;
       public         heap r       postgres    false            �            1259    41953 !   asignacion_ruta_id_asignacion_seq    SEQUENCE     �   ALTER TABLE public.asignacion_ruta ALTER COLUMN id_asignacion ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.asignacion_ruta_id_asignacion_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    240            �            1259    41782    demanda    TABLE     %  CREATE TABLE public.demanda (
    id_demanda bigint NOT NULL,
    cantidad integer NOT NULL,
    id_usuario bigint NOT NULL,
    descripcion character varying(255) NOT NULL,
    fin_ventana timestamp(6) without time zone NOT NULL,
    inicio_ventana timestamp(6) without time zone NOT NULL
);
    DROP TABLE public.demanda;
       public         heap r       postgres    false            �            1259    41781    demanda_id_demanda_seq    SEQUENCE     �   ALTER TABLE public.demanda ALTER COLUMN id_demanda ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.demanda_id_demanda_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    218            �            1259    41788    detalle_reporte_demanda    TABLE     �   CREATE TABLE public.detalle_reporte_demanda (
    id bigint NOT NULL,
    periodo character varying(50) NOT NULL,
    id_demanda bigint NOT NULL,
    id_reporte bigint NOT NULL
);
 +   DROP TABLE public.detalle_reporte_demanda;
       public         heap r       postgres    false            �            1259    41787    detalle_reporte_demanda_id_seq    SEQUENCE     �   ALTER TABLE public.detalle_reporte_demanda ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.detalle_reporte_demanda_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    220            �            1259    41794    detalle_reportes_rutas    TABLE     �   CREATE TABLE public.detalle_reportes_rutas (
    id bigint NOT NULL,
    entregas_tardias integer NOT NULL,
    id_reporte bigint NOT NULL,
    id_ruta bigint NOT NULL
);
 *   DROP TABLE public.detalle_reportes_rutas;
       public         heap r       postgres    false            �            1259    41793    detalle_reportes_rutas_id_seq    SEQUENCE     �   ALTER TABLE public.detalle_reportes_rutas ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.detalle_reportes_rutas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    222            �            1259    41800 
   itinerario    TABLE     5  CREATE TABLE public.itinerario (
    id_itinerario bigint NOT NULL,
    estado character varying(20) NOT NULL,
    hora_llegada_estimada time(6) without time zone,
    hora_salida_estimada time(6) without time zone,
    orden integer NOT NULL,
    id_ruta bigint NOT NULL,
    id_ubicacion bigint NOT NULL
);
    DROP TABLE public.itinerario;
       public         heap r       postgres    false            �            1259    41799    itinerario_id_itinerario_seq    SEQUENCE     �   ALTER TABLE public.itinerario ALTER COLUMN id_itinerario ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.itinerario_id_itinerario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    224            �            1259    42004    oferta    TABLE     �   CREATE TABLE public.oferta (
    id_oferta bigint NOT NULL,
    cantidad integer NOT NULL,
    estado character varying(255) NOT NULL,
    nombre character varying(100) NOT NULL,
    id_usuario bigint NOT NULL
);
    DROP TABLE public.oferta;
       public         heap r       postgres    false            �            1259    42003    oferta_id_oferta_seq    SEQUENCE     �   ALTER TABLE public.oferta ALTER COLUMN id_oferta ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.oferta_id_oferta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    244            �            1259    41998    parametros_demanda    TABLE     �   CREATE TABLE public.parametros_demanda (
    id_parametro bigint NOT NULL,
    alpha double precision NOT NULL,
    gamma double precision NOT NULL,
    nombre character varying(255) NOT NULL
);
 &   DROP TABLE public.parametros_demanda;
       public         heap r       postgres    false            �            1259    41997 #   parametros_demanda_id_parametro_seq    SEQUENCE     �   ALTER TABLE public.parametros_demanda ALTER COLUMN id_parametro ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.parametros_demanda_id_parametro_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    242            �            1259    41814    pedido    TABLE       CREATE TABLE public.pedido (
    id_pedido bigint NOT NULL,
    estado character varying(20) NOT NULL,
    fecha_entrega timestamp(6) without time zone,
    fecha_pedido timestamp(6) without time zone NOT NULL,
    total_pedido numeric(10,2),
    id_cliente bigint NOT NULL
);
    DROP TABLE public.pedido;
       public         heap r       postgres    false            �            1259    41813    pedido_id_pedido_seq    SEQUENCE     �   ALTER TABLE public.pedido ALTER COLUMN id_pedido ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.pedido_id_pedido_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    226            �            1259    42010 
   prediccion    TABLE     �   CREATE TABLE public.prediccion (
    id bigint NOT NULL,
    nombre character varying(100) NOT NULL,
    precio double precision NOT NULL,
    prediccion oid NOT NULL,
    rango_meses integer NOT NULL,
    id_parametros_demanda bigint NOT NULL
);
    DROP TABLE public.prediccion;
       public         heap r       postgres    false            �            1259    42009    prediccion_id_seq    SEQUENCE     �   ALTER TABLE public.prediccion ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.prediccion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    246            �            1259    41820    reporte    TABLE     �  CREATE TABLE public.reporte (
    id bigint NOT NULL,
    descripcion text,
    fecha_generacion date NOT NULL,
    nombre_reporte character varying(255) NOT NULL,
    ruta_archivo character varying(255) NOT NULL,
    tipo_reporte character varying(50) NOT NULL,
    CONSTRAINT reporte_tipo_reporte_check CHECK (((tipo_reporte)::text = ANY ((ARRAY['DEMANDA'::character varying, 'RUTAS'::character varying])::text[])))
);
    DROP TABLE public.reporte;
       public         heap r       postgres    false            �            1259    41819    reporte_id_seq    SEQUENCE     �   ALTER TABLE public.reporte ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.reporte_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    228            �            1259    41829    ruta    TABLE     �   CREATE TABLE public.ruta (
    id_ruta bigint NOT NULL,
    costo numeric(10,2),
    distancia_total numeric(10,2),
    estado character varying(20) NOT NULL,
    fecha date NOT NULL,
    tiempo_total numeric(10,2),
    id_vehiculo bigint NOT NULL
);
    DROP TABLE public.ruta;
       public         heap r       postgres    false            �            1259    41828    ruta_id_ruta_seq    SEQUENCE     �   ALTER TABLE public.ruta ALTER COLUMN id_ruta ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ruta_id_ruta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    230            �            1259    41835    sucursal    TABLE     �   CREATE TABLE public.sucursal (
    id_sucursal bigint NOT NULL,
    nombre character varying(100) NOT NULL,
    id_ubicacion bigint NOT NULL,
    id_usuario bigint NOT NULL
);
    DROP TABLE public.sucursal;
       public         heap r       postgres    false            �            1259    41834    sucursal_id_sucursal_seq    SEQUENCE     �   ALTER TABLE public.sucursal ALTER COLUMN id_sucursal ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sucursal_id_sucursal_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    232            �            1259    41841 	   ubicacion    TABLE     �   CREATE TABLE public.ubicacion (
    id bigint NOT NULL,
    fecha_registro date NOT NULL,
    latitud numeric(10,7) NOT NULL,
    longitud numeric(10,7) NOT NULL,
    nombre character varying(255) NOT NULL
);
    DROP TABLE public.ubicacion;
       public         heap r       postgres    false            �            1259    41840    ubicacion_id_seq    SEQUENCE     �   ALTER TABLE public.ubicacion ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ubicacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    234            �            1259    41847    usuario    TABLE     2  CREATE TABLE public.usuario (
    id bigint NOT NULL,
    contacto integer NOT NULL,
    email character varying(255) NOT NULL,
    fecha_creacion timestamp(6) without time zone NOT NULL,
    latitud numeric(10,7) NOT NULL,
    longitud numeric(10,7) NOT NULL,
    nombre character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    tipo_usuario character varying(255) NOT NULL,
    CONSTRAINT usuario_tipo_usuario_check CHECK (((tipo_usuario)::text = ANY ((ARRAY['CLIENTE'::character varying, 'SUCURSAL'::character varying])::text[])))
);
    DROP TABLE public.usuario;
       public         heap r       postgres    false            �            1259    41846    usuario_id_seq    SEQUENCE     �   ALTER TABLE public.usuario ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    236            �            1259    41941    vehiculo    TABLE     2  CREATE TABLE public.vehiculo (
    id_vehiculo bigint NOT NULL,
    capacidad_kg integer NOT NULL,
    disponibilidad character varying(20) NOT NULL,
    fechade_creacion timestamp(6) without time zone NOT NULL,
    placa character varying(50) NOT NULL,
    tipo_vehiculo character varying(50) NOT NULL
);
    DROP TABLE public.vehiculo;
       public         heap r       postgres    false            �            1259    41940    vehiculo_id_vehiculo_seq    SEQUENCE     �   ALTER TABLE public.vehiculo ALTER COLUMN id_vehiculo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.vehiculo_id_vehiculo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    238            �           2613    42028    42028..42040    BLOB METADATA     N   42028
42029
42030
42031
42032
42033
42034
42035
42036
42037
42038
42039
42040
    -- dummy                     postgres    false            z          0    41954    asignacion_ruta 
   TABLE DATA           x   COPY public.asignacion_ruta (id_asignacion, detalles, estado, fechade_asignacion, nombre_ruta, vehiculo_id) FROM stdin;
    public               postgres    false    240   �       d          0    41782    demanda 
   TABLE DATA           m   COPY public.demanda (id_demanda, cantidad, id_usuario, descripcion, fin_ventana, inicio_ventana) FROM stdin;
    public               postgres    false    218   �       f          0    41788    detalle_reporte_demanda 
   TABLE DATA           V   COPY public.detalle_reporte_demanda (id, periodo, id_demanda, id_reporte) FROM stdin;
    public               postgres    false    220   _�       h          0    41794    detalle_reportes_rutas 
   TABLE DATA           [   COPY public.detalle_reportes_rutas (id, entregas_tardias, id_reporte, id_ruta) FROM stdin;
    public               postgres    false    222   |�       j          0    41800 
   itinerario 
   TABLE DATA           �   COPY public.itinerario (id_itinerario, estado, hora_llegada_estimada, hora_salida_estimada, orden, id_ruta, id_ubicacion) FROM stdin;
    public               postgres    false    224   ��       ~          0    42004    oferta 
   TABLE DATA           Q   COPY public.oferta (id_oferta, cantidad, estado, nombre, id_usuario) FROM stdin;
    public               postgres    false    244   ��       |          0    41998    parametros_demanda 
   TABLE DATA           P   COPY public.parametros_demanda (id_parametro, alpha, gamma, nombre) FROM stdin;
    public               postgres    false    242   �       l          0    41814    pedido 
   TABLE DATA           j   COPY public.pedido (id_pedido, estado, fecha_entrega, fecha_pedido, total_pedido, id_cliente) FROM stdin;
    public               postgres    false    226   4�       �          0    42010 
   prediccion 
   TABLE DATA           h   COPY public.prediccion (id, nombre, precio, prediccion, rango_meses, id_parametros_demanda) FROM stdin;
    public               postgres    false    246   Q�       n          0    41820    reporte 
   TABLE DATA           p   COPY public.reporte (id, descripcion, fecha_generacion, nombre_reporte, ruta_archivo, tipo_reporte) FROM stdin;
    public               postgres    false    228   
�       p          0    41829    ruta 
   TABLE DATA           i   COPY public.ruta (id_ruta, costo, distancia_total, estado, fecha, tiempo_total, id_vehiculo) FROM stdin;
    public               postgres    false    230   '�       r          0    41835    sucursal 
   TABLE DATA           Q   COPY public.sucursal (id_sucursal, nombre, id_ubicacion, id_usuario) FROM stdin;
    public               postgres    false    232   D�       t          0    41841 	   ubicacion 
   TABLE DATA           R   COPY public.ubicacion (id, fecha_registro, latitud, longitud, nombre) FROM stdin;
    public               postgres    false    234   a�       v          0    41847    usuario 
   TABLE DATA           y   COPY public.usuario (id, contacto, email, fecha_creacion, latitud, longitud, nombre, password, tipo_usuario) FROM stdin;
    public               postgres    false    236   ~�       x          0    41941    vehiculo 
   TABLE DATA           u   COPY public.vehiculo (id_vehiculo, capacidad_kg, disponibilidad, fechade_creacion, placa, tipo_vehiculo) FROM stdin;
    public               postgres    false    238   ��       �           0    0 !   asignacion_ruta_id_asignacion_seq    SEQUENCE SET     O   SELECT pg_catalog.setval('public.asignacion_ruta_id_asignacion_seq', 4, true);
          public               postgres    false    239            �           0    0    demanda_id_demanda_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.demanda_id_demanda_seq', 3, true);
          public               postgres    false    217            �           0    0    detalle_reporte_demanda_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.detalle_reporte_demanda_id_seq', 1, false);
          public               postgres    false    219            �           0    0    detalle_reportes_rutas_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.detalle_reportes_rutas_id_seq', 1, false);
          public               postgres    false    221            �           0    0    itinerario_id_itinerario_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.itinerario_id_itinerario_seq', 1, false);
          public               postgres    false    223            �           0    0    oferta_id_oferta_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.oferta_id_oferta_seq', 2, true);
          public               postgres    false    243            �           0    0 #   parametros_demanda_id_parametro_seq    SEQUENCE SET     Q   SELECT pg_catalog.setval('public.parametros_demanda_id_parametro_seq', 1, true);
          public               postgres    false    241            �           0    0    pedido_id_pedido_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.pedido_id_pedido_seq', 1, false);
          public               postgres    false    225            �           0    0    prediccion_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.prediccion_id_seq', 13, true);
          public               postgres    false    245            �           0    0    reporte_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.reporte_id_seq', 1, false);
          public               postgres    false    227            �           0    0    ruta_id_ruta_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.ruta_id_ruta_seq', 1, false);
          public               postgres    false    229            �           0    0    sucursal_id_sucursal_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.sucursal_id_sucursal_seq', 1, false);
          public               postgres    false    231            �           0    0    ubicacion_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.ubicacion_id_seq', 1, false);
          public               postgres    false    233            �           0    0    usuario_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.usuario_id_seq', 14, true);
          public               postgres    false    235            �           0    0    vehiculo_id_vehiculo_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.vehiculo_id_vehiculo_seq', 6, true);
          public               postgres    false    237            �          0    0    42028..42040    BLOBS                               postgres    false    4993    4995   s�       �           2606    41960 $   asignacion_ruta asignacion_ruta_pkey 
   CONSTRAINT     m   ALTER TABLE ONLY public.asignacion_ruta
    ADD CONSTRAINT asignacion_ruta_pkey PRIMARY KEY (id_asignacion);
 N   ALTER TABLE ONLY public.asignacion_ruta DROP CONSTRAINT asignacion_ruta_pkey;
       public                 postgres    false    240            �           2606    41786    demanda demanda_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.demanda
    ADD CONSTRAINT demanda_pkey PRIMARY KEY (id_demanda);
 >   ALTER TABLE ONLY public.demanda DROP CONSTRAINT demanda_pkey;
       public                 postgres    false    218            �           2606    41792 4   detalle_reporte_demanda detalle_reporte_demanda_pkey 
   CONSTRAINT     r   ALTER TABLE ONLY public.detalle_reporte_demanda
    ADD CONSTRAINT detalle_reporte_demanda_pkey PRIMARY KEY (id);
 ^   ALTER TABLE ONLY public.detalle_reporte_demanda DROP CONSTRAINT detalle_reporte_demanda_pkey;
       public                 postgres    false    220            �           2606    41798 2   detalle_reportes_rutas detalle_reportes_rutas_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY public.detalle_reportes_rutas
    ADD CONSTRAINT detalle_reportes_rutas_pkey PRIMARY KEY (id);
 \   ALTER TABLE ONLY public.detalle_reportes_rutas DROP CONSTRAINT detalle_reportes_rutas_pkey;
       public                 postgres    false    222            �           2606    41804    itinerario itinerario_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.itinerario
    ADD CONSTRAINT itinerario_pkey PRIMARY KEY (id_itinerario);
 D   ALTER TABLE ONLY public.itinerario DROP CONSTRAINT itinerario_pkey;
       public                 postgres    false    224            �           2606    42008    oferta oferta_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.oferta
    ADD CONSTRAINT oferta_pkey PRIMARY KEY (id_oferta);
 <   ALTER TABLE ONLY public.oferta DROP CONSTRAINT oferta_pkey;
       public                 postgres    false    244            �           2606    42002 *   parametros_demanda parametros_demanda_pkey 
   CONSTRAINT     r   ALTER TABLE ONLY public.parametros_demanda
    ADD CONSTRAINT parametros_demanda_pkey PRIMARY KEY (id_parametro);
 T   ALTER TABLE ONLY public.parametros_demanda DROP CONSTRAINT parametros_demanda_pkey;
       public                 postgres    false    242            �           2606    41818    pedido pedido_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (id_pedido);
 <   ALTER TABLE ONLY public.pedido DROP CONSTRAINT pedido_pkey;
       public                 postgres    false    226            �           2606    42014    prediccion prediccion_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.prediccion
    ADD CONSTRAINT prediccion_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.prediccion DROP CONSTRAINT prediccion_pkey;
       public                 postgres    false    246            �           2606    41827    reporte reporte_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.reporte
    ADD CONSTRAINT reporte_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.reporte DROP CONSTRAINT reporte_pkey;
       public                 postgres    false    228            �           2606    41833    ruta ruta_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.ruta
    ADD CONSTRAINT ruta_pkey PRIMARY KEY (id_ruta);
 8   ALTER TABLE ONLY public.ruta DROP CONSTRAINT ruta_pkey;
       public                 postgres    false    230            �           2606    41839    sucursal sucursal_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.sucursal
    ADD CONSTRAINT sucursal_pkey PRIMARY KEY (id_sucursal);
 @   ALTER TABLE ONLY public.sucursal DROP CONSTRAINT sucursal_pkey;
       public                 postgres    false    232            �           2606    41845    ubicacion ubicacion_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.ubicacion
    ADD CONSTRAINT ubicacion_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.ubicacion DROP CONSTRAINT ubicacion_pkey;
       public                 postgres    false    234            �           2606    41866 #   usuario uk5171l57faosmj8myawaucatdw 
   CONSTRAINT     _   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uk5171l57faosmj8myawaucatdw UNIQUE (email);
 M   ALTER TABLE ONLY public.usuario DROP CONSTRAINT uk5171l57faosmj8myawaucatdw;
       public                 postgres    false    236            �           2606    42016 "   oferta ukjnb1rbg1w8y1xueqqmjrp5n4k 
   CONSTRAINT     c   ALTER TABLE ONLY public.oferta
    ADD CONSTRAINT ukjnb1rbg1w8y1xueqqmjrp5n4k UNIQUE (id_usuario);
 L   ALTER TABLE ONLY public.oferta DROP CONSTRAINT ukjnb1rbg1w8y1xueqqmjrp5n4k;
       public                 postgres    false    244            �           2606    41864 $   sucursal ukp8iyib5yw6241h1c4cu4hn7o6 
   CONSTRAINT     g   ALTER TABLE ONLY public.sucursal
    ADD CONSTRAINT ukp8iyib5yw6241h1c4cu4hn7o6 UNIQUE (id_ubicacion);
 N   ALTER TABLE ONLY public.sucursal DROP CONSTRAINT ukp8iyib5yw6241h1c4cu4hn7o6;
       public                 postgres    false    232            �           2606    41947 $   vehiculo ukqt70ab7dm6c22avc6b4koit9c 
   CONSTRAINT     `   ALTER TABLE ONLY public.vehiculo
    ADD CONSTRAINT ukqt70ab7dm6c22avc6b4koit9c UNIQUE (placa);
 N   ALTER TABLE ONLY public.vehiculo DROP CONSTRAINT ukqt70ab7dm6c22avc6b4koit9c;
       public                 postgres    false    238            �           2606    41854    usuario usuario_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public                 postgres    false    236            �           2606    41945    vehiculo vehiculo_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.vehiculo
    ADD CONSTRAINT vehiculo_pkey PRIMARY KEY (id_vehiculo);
 @   ALTER TABLE ONLY public.vehiculo DROP CONSTRAINT vehiculo_pkey;
       public                 postgres    false    238            �           2606    41889 3   detalle_reporte_demanda fk2dy7l86s61f0c9ryxr9xi2glv    FK CONSTRAINT     �   ALTER TABLE ONLY public.detalle_reporte_demanda
    ADD CONSTRAINT fk2dy7l86s61f0c9ryxr9xi2glv FOREIGN KEY (id_reporte) REFERENCES public.reporte(id);
 ]   ALTER TABLE ONLY public.detalle_reporte_demanda DROP CONSTRAINT fk2dy7l86s61f0c9ryxr9xi2glv;
       public               postgres    false    228    220    4778            �           2606    41961 *   asignacion_ruta fk5lmydydtmr8fglsd5kd4ne8b    FK CONSTRAINT     �   ALTER TABLE ONLY public.asignacion_ruta
    ADD CONSTRAINT fk5lmydydtmr8fglsd5kd4ne8b FOREIGN KEY (vehiculo_id) REFERENCES public.vehiculo(id_vehiculo);
 T   ALTER TABLE ONLY public.asignacion_ruta DROP CONSTRAINT fk5lmydydtmr8fglsd5kd4ne8b;
       public               postgres    false    4794    238    240            �           2606    41879 #   demanda fk5q5a2pvipcxnxxpg7ua5igf6w    FK CONSTRAINT     �   ALTER TABLE ONLY public.demanda
    ADD CONSTRAINT fk5q5a2pvipcxnxxpg7ua5igf6w FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);
 M   ALTER TABLE ONLY public.demanda DROP CONSTRAINT fk5q5a2pvipcxnxxpg7ua5igf6w;
       public               postgres    false    236    4790    218            �           2606    42022 &   prediccion fk7e6mt52l5h4uwpxhelckh5i39    FK CONSTRAINT     �   ALTER TABLE ONLY public.prediccion
    ADD CONSTRAINT fk7e6mt52l5h4uwpxhelckh5i39 FOREIGN KEY (id_parametros_demanda) REFERENCES public.parametros_demanda(id_parametro);
 P   ALTER TABLE ONLY public.prediccion DROP CONSTRAINT fk7e6mt52l5h4uwpxhelckh5i39;
       public               postgres    false    4798    242    246            �           2606    41899 2   detalle_reportes_rutas fk863ex7fmlan54p4arowjitey7    FK CONSTRAINT     �   ALTER TABLE ONLY public.detalle_reportes_rutas
    ADD CONSTRAINT fk863ex7fmlan54p4arowjitey7 FOREIGN KEY (id_ruta) REFERENCES public.ruta(id_ruta);
 \   ALTER TABLE ONLY public.detalle_reportes_rutas DROP CONSTRAINT fk863ex7fmlan54p4arowjitey7;
       public               postgres    false    230    4780    222            �           2606    41894 2   detalle_reportes_rutas fk8k9wex4wi9htpe7xa7q45q11r    FK CONSTRAINT     �   ALTER TABLE ONLY public.detalle_reportes_rutas
    ADD CONSTRAINT fk8k9wex4wi9htpe7xa7q45q11r FOREIGN KEY (id_reporte) REFERENCES public.reporte(id);
 \   ALTER TABLE ONLY public.detalle_reportes_rutas DROP CONSTRAINT fk8k9wex4wi9htpe7xa7q45q11r;
       public               postgres    false    222    4778    228            �           2606    41924 $   sucursal fkem03erqk3spu84p8e3i2j9kkx    FK CONSTRAINT     �   ALTER TABLE ONLY public.sucursal
    ADD CONSTRAINT fkem03erqk3spu84p8e3i2j9kkx FOREIGN KEY (id_ubicacion) REFERENCES public.ubicacion(id);
 N   ALTER TABLE ONLY public.sucursal DROP CONSTRAINT fkem03erqk3spu84p8e3i2j9kkx;
       public               postgres    false    232    234    4786            �           2606    41904 &   itinerario fkgal5h83gn7opxu8u14l684bar    FK CONSTRAINT     �   ALTER TABLE ONLY public.itinerario
    ADD CONSTRAINT fkgal5h83gn7opxu8u14l684bar FOREIGN KEY (id_ruta) REFERENCES public.ruta(id_ruta);
 P   ALTER TABLE ONLY public.itinerario DROP CONSTRAINT fkgal5h83gn7opxu8u14l684bar;
       public               postgres    false    4780    224    230            �           2606    42017 !   oferta fkhu443154h84gs6se82nll7gg    FK CONSTRAINT     �   ALTER TABLE ONLY public.oferta
    ADD CONSTRAINT fkhu443154h84gs6se82nll7gg FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);
 K   ALTER TABLE ONLY public.oferta DROP CONSTRAINT fkhu443154h84gs6se82nll7gg;
       public               postgres    false    236    244    4790            �           2606    41929 $   sucursal fkn8tg01imun7gtj3wglyo1a0vf    FK CONSTRAINT     �   ALTER TABLE ONLY public.sucursal
    ADD CONSTRAINT fkn8tg01imun7gtj3wglyo1a0vf FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);
 N   ALTER TABLE ONLY public.sucursal DROP CONSTRAINT fkn8tg01imun7gtj3wglyo1a0vf;
       public               postgres    false    236    4790    232            �           2606    41884 3   detalle_reporte_demanda fkqg3s8ge0cgrp4usasae5m0rpo    FK CONSTRAINT     �   ALTER TABLE ONLY public.detalle_reporte_demanda
    ADD CONSTRAINT fkqg3s8ge0cgrp4usasae5m0rpo FOREIGN KEY (id_demanda) REFERENCES public.demanda(id_demanda);
 ]   ALTER TABLE ONLY public.detalle_reporte_demanda DROP CONSTRAINT fkqg3s8ge0cgrp4usasae5m0rpo;
       public               postgres    false    220    4768    218            �           2606    41948     ruta fkro4h36vg00lug6o2tinvbl2xn    FK CONSTRAINT     �   ALTER TABLE ONLY public.ruta
    ADD CONSTRAINT fkro4h36vg00lug6o2tinvbl2xn FOREIGN KEY (id_vehiculo) REFERENCES public.vehiculo(id_vehiculo);
 J   ALTER TABLE ONLY public.ruta DROP CONSTRAINT fkro4h36vg00lug6o2tinvbl2xn;
       public               postgres    false    4794    238    230            �           2606    41909 &   itinerario fkrpyo6r7jfdvp14hn2hbygh1wm    FK CONSTRAINT     �   ALTER TABLE ONLY public.itinerario
    ADD CONSTRAINT fkrpyo6r7jfdvp14hn2hbygh1wm FOREIGN KEY (id_ubicacion) REFERENCES public.ubicacion(id);
 P   ALTER TABLE ONLY public.itinerario DROP CONSTRAINT fkrpyo6r7jfdvp14hn2hbygh1wm;
       public               postgres    false    224    4786    234            z   �   x���=n!�N��h~ Wp�������k���?���uҎ>��<iȝ�GZ�b�N��R��ʚ�� 8L"��KBH)�(��L;�2���I1��qN�ybŲ�X�����:�i�U��Ĭ����]����гS)��N.�ڡ�X"���9΁�z�/'@��U ���'wԶv�۶>���%�����l��      d   c   x�mͱ�0�ڙ"���L� ���I��B�&��^zP&���~�~6R��P�!U<c'��R���F���B�,U͡��G��4�v?nS��$;      f      x������ � �      h      x������ � �      j      x������ � �      ~   <   x�3�420�tt�s��OK-*ITHIUK-J����2�4�&��W��ZTa����� �]      |   "   x�3�4�36054�4�y�e�)�)\1z\\\ S!�      l      x������ � �      �   �   x�m�Q
� ����k����2���� �+����`C1�%�	I�ǔ�)��Σg�d��y[pX�J�����<�m=��*��\G-w�T$뷒�J#��5SQ���zu��o�Y�70(���]RI�#v<�P��B�bl����eO��M���nlR�'��];c�?9A�      n      x������ � �      p      x������ � �      r      x������ � �      t      x������ � �      v     x����n�@���)x�����:��rhUQ��zY�����F}��X���IS���$���xHۋ�G���u��m�ц� K
Ml�Pm49�Wb@��VKkK`;]����_}�S�ih��&�S�W�����绫�&�\�Q��DC&�{�Xa"ǋ�o����0���Ф��٤���h�
�!�+}`~ɰ�4��5���Uj���:մ��g�*��_Ќ���9>��G��-�f5��O�J};�ž.V�]����ʲ�Q���
g
�!�HTjf4�D�3����~T;ռ����r��z!9�Ym����ޓ\�ĥuN���� ����{W%ߝ�*��@���S�HF����*G�|�5˄��#�BL: ���+����Y�vx:�O9��9H �d��!�BU���F��Ed
s�ളG�\�N�j��z��J�YA����~��^,$B�����z���s��-�S�AaF��3D��Eĩ��-�
��ZΒ=i�;�A�b��VJd����-��V       x   �   x�}�K��0��N�%�#�5�̖M�6�J�A�2�'�J���,�,�qZ�u��� h�;������0��>Z�}Ʌ� o�z��iVdv��0h�b��:�O���K?(�Nv�&��ʸ�~\a��}������Oٵ/��&%w��$f�Y�8���3ԥ*�mLH�����i�����E+��E�Vl      �   ,�   O   x��M-V0�RpI�M�KIT�U�52��464�42�32���*0BQ`fbnaajfhhb`flinj@e�g` ���Z�D?^. i &      -�   T   x��M-V0�RpI�M�KIT�U�576����43�36������ e-ML��LL�--��� �#[��5�*j�!
 �I0      .�   T   x��M-V0�RpI�M�KIT�U�576����43�36������ e-ML��LL�--��� �#[��5�*j�!
 �I0      /�   O   x��M-V0�RpI�M�KIT�U�52��464�42�32���*0BQ`fbnaajfhhb`flinj@e�g` ���Z�D?^. i &      0�   E   x��M-V0�RpI�M�KIT�U�576����43�36������ e-ML��LL�--���x� I�U      1�   W   x��M-V0�RpI�M�KIT�U�0602�3����J!K��X����[X�������D�1�ϼ�̼̒J��	��_�/ �  h      2�   "   x��M-V0�RpI�M�KIT�U0�45�31�� c1u      3�   P   x��M-V0�RpI�M�KIT�U�5145��4�32��������Y�����z�����K􃈚`� IV#"      4�   \   x��M-V0�RpI�M�KIT�U�527616755315�3����*1BQbhjjhhfnb`fiddhlbn@g�g` q�1���� �&XEM���a� �0>�      5�   ^   x��M-V0�RpI�M�KIT�U�52�00406�45�01�31���*2BQdhhdjjl`bjii`fb`ij0Ȁ��������K􃈚`5�*j�!
 ��E      6�   ]   x��M-V0�RpI�M�KIT�U�57732405307׳0����0BQadnjniaifhflfjbfi@'�g` q�1�s�� �&XEM���a5� <�C      7�   b   x��M-V0�RpI�M�KIT�U�527616755315�3����*1BQbhjjhhfnb`fiddhlbn@g�g` q�1���� �&XEM���a5�*j�!
 �\J�      8�   X   x��M-V0�RpI�M�KIT�U�53�4226115�3����*0BQ`jnhfiajjdbijfnfa@�g` ���r�D?��	VQS��f� Ӹ9Y          