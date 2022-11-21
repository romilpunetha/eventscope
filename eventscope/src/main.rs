use actix_web::cookie::time;
use actix_web::http::header::ContentType;
use actix_web::web::Json;
use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder};
use flatten_serde_json::flatten;
use serde::{Deserialize, Serialize};
use serde_json::{Number, Value};
use time::OffsetDateTime;
use uuid::Uuid;

#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct EventInput {
    uuid: Uuid,
    event_name: String,
    properties: Value,
}

#[derive(Debug, Serialize, Deserialize)]
pub struct Event {
    uuid: Uuid,
    event_name: String,
    // _namespace: String,
    _source: String,
    _timestamp: OffsetDateTime,
    _created_at: OffsetDateTime,
    string_names: Vec<String>,
    string_values: Vec<String>,
    number_names: Vec<String>,
    number_values: Vec<Number>,
    bool_names: Vec<String>,
    bool_values: Vec<bool>,
    array_names: Vec<String>,
    array_values: Vec<Vec<String>>,
    // team_id: String,
    // distinct_id: String,
    // created_at: OffsetDateTime,
    // person_id: String,
    // person_created_at: OffsetDateTime,
    // person_properties: String,
}

impl Event {
    pub fn new(event_input: EventInput) -> Event {
        let flattened_properties = flatten(&std::mem::take(
            event_input.clone().properties.as_object_mut().unwrap(),
        ));
        let mut event = Event {
            uuid: event_input.uuid,
            event_name: event_input.event_name,
            _source: event_input.properties.to_string(),
            _timestamp: OffsetDateTime::now_utc(),
            _created_at: OffsetDateTime::now_utc(),
            string_names: vec![],
            string_values: vec![],
            number_names: vec![],
            number_values: vec![],
            bool_names: vec![],
            bool_values: vec![],
            array_names: vec![],
            array_values: vec![],
        };
        for key in flattened_properties.keys() {
            match &flattened_properties[key] {
                Value::Bool(value) => {
                    event.bool_names.push(key.to_owned());
                    event.bool_values.push(*value);
                }
                Value::Number(value) => {
                    event.number_names.push(key.to_owned());
                    event.number_values.push(value.clone());
                }
                Value::String(value) => {
                    event.string_names.push(key.to_owned());
                    event.string_values.push(value.clone());
                }
                Value::Array(value) => {
                    event.array_names.push(key.to_owned());
                    event.array_values.push(
                        value
                            .iter()
                            .map(|x| match x {
                                Value::String(value) => value.to_owned(),
                                default => default.to_string(),
                            })
                            .collect(),
                    );
                }
                _ => {}
            }
        }
        event
    }
}

#[get("/")]
async fn hello() -> impl Responder {
    HttpResponse::Ok().body("Hello world!")
}

#[post("/echo")]
async fn echo(req_body: String) -> impl Responder {
    HttpResponse::Ok().body(req_body)
}

#[post("/events")]
async fn index(event_input: Json<EventInput>) -> HttpResponse {
    let event: Event = Event::new(event_input.to_owned());

    println!("{:#?}", event);

    HttpResponse::Ok()
        .content_type(ContentType::json())
        .body(format!("{:#?}", event))
}

async fn manual_hello() -> impl Responder {
    HttpResponse::Ok().body("Hey there!")
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        App::new()
            .service(hello)
            .service(echo)
            .service(index)
            .route("/hey", web::get().to(manual_hello))
    })
    .bind(("127.0.0.1", 8080))?
    .run()
    .await
}
