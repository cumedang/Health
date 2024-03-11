package utill

import (
	"database/sql"
	"log"
)

func Error(err error) {
	if err != nil {
		log.Panic(err)
	}
}

func CheckId(id string) bool {
	db, err := sql.Open("mysql", "healthuser:1234@tcp(127.0.0.1:3306)/health")
	Error(err)
	defer db.Close()
	var fid string
	err = db.QueryRow("select id from member where id = ?", id).Scan(&fid)

	if err != nil {
		return true
	}
	return false
} //아이디 중복체크

func CheckName(name string) bool {
	db, err := sql.Open("mysql", "healthuser:1234@tcp(127.0.0.1:3306)/health")
	Error(err)
	defer db.Close()
	var fname string
	err = db.QueryRow("select name from member where name = ?", name).Scan(&fname)

	if err != nil {
		return true
	}
	return false
} //이름 중복체크
