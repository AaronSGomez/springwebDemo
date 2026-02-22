# NOVAPAY · Backend Fiscal TPV · Guía Maestra

---

## Stack

| Componente | Tecnología | Versión |
|---|---|---|
| Lenguaje | Java (Eclipse Temurin) | 21 LTS |
| Framework | Spring Boot | 3.5.11 |
| Build | Maven wrapper (`mvnw`) | gestionado por Boot parent |
| Base de datos | PostgreSQL (Docker) | 17 |
| Migraciones | Flyway | 10.x (via Boot BOM) |
| ORM | Hibernate / Spring Data JPA | 6.6.x |
| Seguridad | Spring Security | 6.4.x |
| Mapeo DTO | MapStruct | 1.5.5.Final |
| Utilidades | Lombok | via Boot BOM |
| XML fiscal | JAXB (jakarta.xml.bind-api + jaxb-runtime) | via Boot BOM |
| Firma XML | Apache Santuario xmlsec | 3.0.3 |
| QR | ZXing core + javase | 3.5.3 |

---

## Proyecto

| Campo | Valor |
|---|---|
| GroupId | `com.novapay` |
| ArtifactId | `novapay-backend` |
| Paquete raíz | `com.novapay.backend` |
| Clase principal | `NovapayBackendApplication` |

---

## Base de Datos

| Parámetro | Valor |
|---|---|
| Host | `localhost:5432` |
| Base de datos | `novapay` |
| Usuario | `novapay` |
| Contraseña | `novapay123` |
| Contenedor Docker | `novapay-db` (postgres:17) |

`application.properties`: datasource apunta a localhost:5432/novapay · `ddl-auto=validate` · `flyway.enabled=true` · `server.port=8080`

---

## Dependencias declaradas en pom.xml

**Starters:** `spring-boot-starter-data-jpa`, `spring-boot-starter-security`, `spring-boot-starter-validation`, `spring-boot-starter-web`

**BD:** `flyway-core`, `flyway-database-postgresql` (obligatorio Flyway 10+), `postgresql` (runtime)

**XML fiscal:** `jakarta.xml.bind-api`, `jaxb-runtime`

**Firma:** `xmlsec:3.0.3`

**QR:** `zxing:core:3.5.3`, `zxing:javase:3.5.3`

**Mapeo:** `mapstruct:1.5.5.Final` + processor en `annotationProcessorPaths` (Lombok primero, MapStruct segundo)

**Utilidades:** `lombok` (optional)

---

## Arquitectura · Paquetes

```
com.novapay.backend/
├── NovapayBackendApplication.java
├── domain/
│   ├── entity/           JPA entities
│   ├── valueObject/      Value Objects inmutables
│   ├── enums/            Enumeraciones de dominio
│   ├── repository/       Interfaces Spring Data JPA
│   └── port/             Interfaces outbound (adaptadores fiscales)
├── application/
│   ├── dto/              Records request / response
│   ├── mapper/           Interfaces MapStruct
│   └── service/          Lógica de negocio + orquestación
├── infrastructure/
│   ├── config/           Beans de configuración (Security, Keystore)
│   └── fiscal/
│       ├── verifactu/    Adaptador AEAT VERIFACTU
│       └── tbai/         Adaptadores TicketBAI (Araba, Bizkaia, Gipuzkoa)
└── api/
    └── controller/       REST controllers
```

---

## Convenciones

- Entidades: `@Entity` + `@Table` + `@Id @GeneratedValue(UUID)` + Lombok `@Getter @Builder @NoArgsConstructor @AllArgsConstructor`
- Enums en BD: siempre `@Enumerated(EnumType.STRING)` — nunca ORDINAL
- Money: siempre `BigDecimal`, `NUMERIC(19,6)` en PostgreSQL, Value Object `Money` en dominio
- Value Objects: inmutables, constructor privado, static factory `of()`, validación en construcción
- DTOs: Java Records — nunca exponer `@Entity` en respuestas REST
- Repositories: interfaces Spring Data JPA en `domain/repository/` — nunca implementaciones manuales salvo query compleja
- Servicios: `@Transactional` en servicios, nunca en controllers
- Schema: solo Flyway gestiona DDL — `V{n}__{descripcion}.sql` en `db/migration/`
- MapStruct: interfaces en `application/mapper/`, el procesador genera implementaciones en compilación
- Imports: siempre `jakarta.*` — nunca `javax.*`

---

## Estado Actual

| Estado | Símbolo |
|---|---|
| Completo | ✅ |
| En progreso | 🔄 |
| Pendiente | ⬜ |

### Guía 0 · Entorno y configuración ✅

- ✅ pom.xml con todas las dependencias y plugins correctos
- ✅ application.properties (UTF-8, ddl-auto=validate, flyway enabled)
- ✅ Contenedor Docker novapay-db (postgres:17)
- ✅ Estructura de paquetes definida

### Guía 1 · Dominio JPA 🔄

**Value Objects** (domain/valueObject/)
- ✅ `Money.java` — BigDecimal 6 dec, inmutable, of()/add()/subtract()/multiply()/toFiscalScale()/isZero()
- ✅ `InvoiceNumber.java` — serie + número, formatted() → "A-00001"
- ✅ `TaxId.java` — valida NIF / CIF / NIE con regex

**Enums** (domain/enums/)
- ✅ `FiscalRecordType` — ALTA, ANULACION, SUBSANACION
- ✅ `FiscalStatus` — PENDIENTE_ENVIO, ENVIANDO, ACEPTADO, RECHAZADO, REINTENTO, ERROR_PERMANENTE, ANULADO
- ✅ `InvoiceStatus` — EMITIDA, ANULADA, RECTIFICADA
- ✅ `InvoiceType` — COMPLETA, SIMPLIFICADA, RECTIFICATIVA, RECTIFICATIVA_SIMPLIFICADA
- ✅ `RectificationType` — DIFERENCIAS, SUSTITUCION
- ✅ `TaxAgency` — AEAT, TBAI_ARABA, TBAI_BIZKAIA, TBAI_GIPUZKOA
- ✅ `TaxType` — IVA, IVA_RECARGO_EQUIVALENCIA, EXENTO, NO_SUJETO, IPSI, IGIC

**Entidades JPA** (domain/entity/) 
- ✅ `Company.java` — empresa emisora: NIF (TaxId VO), nombre, dirección, agencia fiscal (TaxAgency)
- ✅ `PosTerminal.java` — terminal TPV: número serie, Company (ManyToOne), activo
- ✅ `Invoice.java` — factura: InvoiceNumber (VO), tipo (InvoiceType), estado (InvoiceStatus), Company, PosTerminal, fechaEmision, baseImponible (Money), cuotaIVA (Money), total (Money), rectificada (self-ref nullable), lista de lineas, lista de desgloses, registro fiscal
- ✅ `InvoiceLine.java` — linea: descripcion, cantidad, precioUnitario (Money), tipoIVA (TaxType), base (Money)
- ✅ `TaxBreakdown.java` — desglose por tipo: TaxType, porcentaje, baseImponible (Money), cuota (Money)
- ✅ `FiscalRecord.java` — registro: Invoice (OneToOne), TaxAgency, hashPrevio, hashActual, xmlEnviado, xmlRespuesta, estado (FiscalStatus), tipo (FiscalRecordType), intentos, timestampEnvio, timestampRespuesta

**Repositorios** (domain/repository/) 
- ✅ `CompanyRepository.java` — findByTaxId
- ✅ `InvoiceRepository.java` — findByCompanyAndStatus, findLastByCompanyForHash
- ✅ `FiscalRecordRepository.java` — findByEstado(FiscalStatus), findByInvoice

**Puertos outbound** (domain/port/) 
- ✅ `FiscalAgencyPort.java` — interfaz: enviar(FiscalRecord) → AgencyResponse, anular(FiscalRecord) → AgencyResponse
- ✅ `AgencyResponse.java` — record: exitoso, codigoRespuesta, descripcion, xmlRespuesta

### Guía 2 · Schema SQL 

- ✅ `V1__init.sql` — crea todas las tablas (companies, pos_terminals, invoices, invoice_lines, tax_breakdowns, fiscal_records) con constraints, índices y FK. **Bloquea el arranque hasta que exista.**

Tablas a crear:

| Tabla | Columnas clave |
|---|---|
| `companies` | id UUID PK, tax_id VARCHAR(9) UNIQUE, nombre, direccion, tax_agency |
| `pos_terminals` | id UUID PK, numero_serie, company_id FK, activo BOOLEAN |
| `invoices` | id UUID PK, serie, numero, tipo, estado, company_id FK, terminal_id FK, fecha_emision, base, cuota_iva, total, rectificada_id FK nullable |
| `invoice_lines` | id UUID PK, invoice_id FK, descripcion, cantidad, precio_unitario, tipo_iva, base |
| `tax_breakdowns` | id UUID PK, invoice_id FK, tax_type, porcentaje, base_imponible, cuota |
| `fiscal_records` | id UUID PK, invoice_id FK UNIQUE, tax_agency, hash_previo, hash_actual, xml_enviado TEXT, xml_respuesta TEXT, estado, tipo, intentos INT, ts_envio, ts_respuesta |

### Guía 3 · API REST y Servicios ⬜

**DTOs** (application/dto/)
- ✅ `InvoiceRequest.java` — record: serie, numero, tipo, companyId, terminalId, fechaEmision, lineas
- ✅ `InvoiceLineRequest.java` — record: descripcion, cantidad, precioUnitario, tipoIVA
- ✅ `InvoiceResponse.java` — record: id, invoiceNumber (formatted), tipo, estado, base, cuotaIVA, total, fechaEmision, registroFiscal
- ✅ `FiscalStatusResponse.java` — record: invoiceId, estado (FiscalStatus), intentos, tsRespuesta

**Mappers** (application/mapper/)
- ⬜ `InvoiceMapper.java` — MapStruct: InvoiceRequest → Invoice, Invoice → InvoiceResponse
- ⬜ `InvoiceLineMapper.java` — MapStruct: InvoiceLineRequest → InvoiceLine

**Servicios** (application/service/)
- ⬜ `InvoiceService.java` — emitirFactura(), anularFactura(), obtenerEstadoFiscal(), reintentarEnvio()
- ⬜ `TaxCalculationService.java` — calcularDesglose(lineas) → lista TaxBreakdown + totales

**Controllers** (api/controller/)
- ⬜ `InvoiceController.java` — POST /invoices, GET /invoices/{id}, POST /invoices/{id}/cancel
- ⬜ `FiscalController.java` — GET /invoices/{id}/fiscal-status, POST /invoices/{id}/retry

**Configuración** (infrastructure/config/)
- ⬜ `SecurityConfig.java` — deshabilitar CSRF para API REST, autenticación básica en dev

### Guía 4 · Hash Fiscal y Encadenamiento ⬜

- ⬜ `HashService.java` (infrastructure/fiscal/) — SHA-256 sobre campos fiscales ordenados, encadenamiento: hashActual = SHA256(hashPrevio + camposFiscalesFactura)
- ⬜ `QrService.java` (infrastructure/fiscal/) — generación QR con ZXing, URL verificación AEAT / TBAI
- ⬜ `FiscalEvidenceService.java` (infrastructure/fiscal/) — orquesta hash + QR, persiste evidencia local antes de enviar
- ⬜ Enriquecimiento de `InvoiceService` — antes de enviar: calcular hash, guardar FiscalRecord en PENDIENTE_ENVIO, luego enviar, actualizar estado

### Guía 5 · Adaptador TicketBAI Álava ⬜

- ⬜ `TbaiXmlBuilder.java` (infrastructure/fiscal/tbai/) — construye XML TicketBAI conforme al esquema XSD de Álava con JAXB
- ⬜ `TbaiSigner.java` (infrastructure/fiscal/tbai/) — firma XAdES-EPES con Apache Santuario sobre el XML generado, usando certificado PKCS#12
- ⬜ `TbaiArabaAdapter.java` (infrastructure/fiscal/tbai/) — implementa `FiscalAgencyPort`, envía XML firmado por HTTPS al endpoint de Álava, parsea respuesta
- ⬜ `TbaiResponseParser.java` (infrastructure/fiscal/tbai/) — extrae código y descripción de la respuesta XML de Hacienda Álava

### Guía 6 · Adaptador VERIFACTU AEAT ⬜

- ⬜ `VerifactuXmlBuilder.java` (infrastructure/fiscal/verifactu/) — construye el mensaje SOAP RegFactuSistemaFacturacion con JAXB, campos obligatorios AEAT
- ⬜ `VerifactuSoapClient.java` (infrastructure/fiscal/verifactu/) — cliente SOAP sobre HTTPS, gestiona certificado de cliente PKCS#12, timeout, reintentos transporte
- ⬜ `VerifactuAdapter.java` (infrastructure/fiscal/verifactu/) — implementa `FiscalAgencyPort`, orquesta build + sign + send, mapea respuesta → AgencyResponse
- ⬜ `VerifactuResponseParser.java` (infrastructure/fiscal/verifactu/) — parsea RespuestaRegFactuSistemaFacturacion, extrae estado (CORRECTO / INCORRECTO) y errores

### Guía 7 · Adaptadores TicketBAI Bizkaia y Gipuzkoa ⬜

- ⬜ `TbaiBizkaiaAdapter.java` — endpoint y esquema XSD específicos de Bizkaia, misma firma XAdES
- ⬜ `TbaiGipuzkoaAdapter.java` — endpoint y esquema XSD específicos de Gipuzkoa
- ⬜ Selector de adaptador en `InvoiceService` según `TaxAgency` de la `Company`

### Guía 8 · Producción y Seguridad ⬜

- ⬜ `CertificateConfig.java` — carga keystore PKCS#12 desde ruta configurable, expone `KeyStore` y `PrivateKey` como beans
- ⬜ `RetryScheduler.java` — `@Scheduled` que busca FiscalRecord en estado REINTENTO y reintenta el envío con back-off
- ⬜ `application-prod.properties` — perfil producción: credenciales externalizadas, endpoints reales AEAT/TBAI, SSL habilitado
- ⬜ Actuator: health, info, metrics — endpoint `/actuator/health` expuesto sin autenticación

---

## Hoja de Ruta

| Guía | Contenido | Estado |
|---|---|---|
| 0 | Entorno · Stack · pom.xml · application.properties | ✅ |
| 1 | Value Objects · Enums · Entidades JPA · Repositorios · Puertos | 🔄 VOs+Enums ✅ · Entidades ⬜ |
| 2 | Schema SQL · V1__init.sql · Flyway | ⬜ bloqueante |
| 3 | DTOs · Mappers · Servicios · Controllers REST · SecurityConfig | ⬜ depende de 1+2 |
| 4 | HashService · QrService · Encadenamiento fiscal · Evidencia local | ⬜ depende de 3 |
| 5 | TicketBAI Álava · XML · Firma XAdES · Envío HTTPS | ⬜ depende de 4 |
| 6 | VERIFACTU AEAT · SOAP · Firma · Estados | ⬜ depende de 4 |
| 7 | TicketBAI Bizkaia y Gipuzkoa · Selector por agencia | ⬜ depende de 5 |
| 8 | Producción · Certificados · Retry scheduler · Actuator | ⬜ depende de 6+7 |

---

*NovaPay Backend · TPV Fiscal VERIFACTU/TicketBAI · Java 21 · Spring Boot 3.5.11*
