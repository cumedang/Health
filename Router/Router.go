package Router

import (
	"Health/utill"
	"database/sql"
	"encoding/json"
	"errors"
	"net/http"
	"time"

	_ "github.com/go-sql-driver/mysql"
	"github.com/gorilla/sessions"
	"github.com/labstack/echo"
	"golang.org/x/crypto/bcrypt"
)

type Food struct {
	Name         string `json:"name"`
	Calories     int    `json:"calories"`
	Link         string `json:"link"`
	Carbohydrate string `json:"carbohydrate"`
	Protein      string `json:"protein"`
	Province     string `json:"province"`
	Vitamin      string `json:"vitamin"`
}
type Sfood struct {
	Name         string `json:"name"`
	Moludi       string `json:"moludi"`
	Calories     int    `json:"calories"`
	Carbohydrate string `json:"carbohydrate"`
	Protein      string `json:"protein"`
	Province     string `json:"province"`
	Vitamin      string `json:"vitamin"`
}

var store = sessions.NewCookieStore([]byte("your-secret-key"))

func IndexHandler(c echo.Context) error {
	return c.File("frontend/index.html")
} //메인화면

func LoginHanddler(c echo.Context) error {
	return c.File("frontend/login.html")
} //로그인화면

func LoginProcees(c echo.Context) error {
	fid := c.FormValue("id")
	fpassword := c.FormValue("pwd")
	session, err := store.Get(c.Request(), "sanss")
	utill.Error(err)
	db, err := sql.Open("mysql", "healthuser:1234@tcp(127.0.0.1:3306)/health")
	utill.Error(err)
	var id string
	var password string
	var name string

	err = db.QueryRow("SELECT id, pw, name from member where id = ?", fid).Scan(&id, &password, &name)
	if err != nil {
		return c.JSON(http.StatusUnauthorized, map[string]interface{}{"success": false, "message": "Login failed. Incorrect username or password."})
	}
	err = bcrypt.CompareHashAndPassword([]byte(password), []byte(fpassword))
	if err != nil {
		return c.JSON(http.StatusUnauthorized, map[string]interface{}{"success": false, "message": "Login failed. Incorrect username or password."})
	}
	if err == nil {
		session.Values["id"] = id
		err = session.Save(c.Request(), c.Response())
		utill.Error(err)
		return c.JSON(http.StatusUnauthorized, map[string]interface{}{"success": true, "message": "Success"})
	} else {
		return c.JSON(http.StatusUnauthorized, map[string]interface{}{"success": false, "message": "Login failed. Incorrect username or password."})
	}

} //로그인

func SignHanddler(c echo.Context) error {
	return c.File("frontend/signup.html")
} //회원가입화면

func SignProcess(c echo.Context) error {
	fid := c.FormValue("id")
	fpassword := c.FormValue("pwd")
	fname := c.FormValue("name")

	if !utill.CheckId(fid) {
		return c.JSON(http.StatusUnauthorized, map[string]interface{}{"success": false, "message": "idError"})
	}

	if !utill.CheckName(fname) {
		return c.JSON(http.StatusUnauthorized, map[string]interface{}{"success": false, "message": "nameError"})
	}

	db, err := sql.Open("mysql", "healthuser:1234@tcp(127.0.0.1:3306)/health")
	utill.Error(err)
	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(fpassword), bcrypt.DefaultCost)
	utill.Error(err)
	db.Query("INSERT INTO member values(?,?,?)", fid, hashedPassword, fname)
	_, err = db.Query("CREATE TABLE " + fid + " (id varchar(50) primary key, day date, foodname varchar(50), ka varchar(50))")
	utill.Error(err)
	defer db.Close()

	return c.JSON(http.StatusUnauthorized, map[string]interface{}{"success": true, "message": "Success"})
}

func DietHanddler(c echo.Context) error {
	return c.File("frontend/moludi.html")
}

func DietProcess(c echo.Context) error {
	db, err := sql.Open("mysql", "healthuser:1234@tcp(127.0.0.1:3306)/health")
	utill.Error(err)
	moludi := c.Request().FormValue("meal")
	fname := c.Request().FormValue("food-name")
	session, err := store.Get(c.Request(), "sanss")
	utill.Error(err)
	idValue := session.Values["id"]
	id, _ := idValue.(string)
	now := time.Now()
	custom := now.Format("2006-01-02")
	_, err = db.Query("insert into "+id+" values(?,?,?)", custom, moludi, fname)
	utill.Error(err)
	return c.File("frontend/moludi.html")
}

func FoodHanddler(c echo.Context) error {
	db, err := sql.Open("mysql", "healthuser:1234@tcp(127.0.0.1:3306)/health")
	utill.Error(err)
	var foods []Food
	row, err := db.Query("SELECT food_name,Calories,photo_link,tan,Prot,Prov,Vita from foods")
	defer row.Close()
	for row.Next() {
		var food Food
		if err = row.Scan(&food.Name, &food.Calories, &food.Link, &food.Carbohydrate, &food.Protein, &food.Province, &food.Vitamin); err != nil {
			http.Error(c.Response().Writer, err.Error(), http.StatusInternalServerError)
			return err
		}
		foods = append(foods, food)
	}
	c.Response().Writer.Header().Set("Content-Type", "application/json")
	if err := json.NewEncoder(c.Response().Writer).Encode(foods); err != nil {
		http.Error(c.Response().Writer, err.Error(), http.StatusInternalServerError)
		return nil
	}
	return nil
}
func SessionHandller(c echo.Context) error {
	session, err := store.Get(c.Request(), "sanss")
	if err != nil {
		http.Error(c.Response().Writer, err.Error(), http.StatusInternalServerError)
		return nil
	}

	// 세션에서 id 값을 가져옴
	id, ok := session.Values["id"].(string)
	if !ok {
		http.Error(c.Response().Writer, "세션에서 ID를 가져올 수 없습니다.", http.StatusInternalServerError)
		return nil
	}

	// JSON 형식으로 응답
	response := struct {
		ID string `json:"id"`
	}{ID: id}

	c.Response().Writer.Header().Set("Content-Type", "application/json")
	json.NewEncoder(c.Response().Writer).Encode(response)
	return nil
}

func FindByDate(c echo.Context) error {
	return c.File("frontend/findByDate.html")
}

func SelectDate(c echo.Context) error {
	db, err := sql.Open("mysql", "healthuser:1234@tcp(127.0.0.1:3306)/health")
	if err != nil {
		http.Error(c.Response().Writer, err.Error(), http.StatusInternalServerError)
		return err
	}
	defer db.Close()

	date := c.QueryParam("date")
	session, err := store.Get(c.Request(), "sanss")
	if err != nil {
		http.Error(c.Response().Writer, err.Error(), http.StatusInternalServerError)
		return err
	}
	idValue := session.Values["id"]
	id, ok := idValue.(string)
	if !ok {
		err := errors.New("Failed to convert id to string")
		http.Error(c.Response().Writer, err.Error(), http.StatusInternalServerError)
		return err
	}

	rows, err := db.Query("SELECT moludi, foodname FROM "+id+" WHERE day = ?", date)
	if err != nil {
		http.Error(c.Response().Writer, err.Error(), http.StatusInternalServerError)
		return err
	}
	defer rows.Close()

	var sFodds []Sfood
	for rows.Next() {
		var sfood Sfood
		if err := rows.Scan(&sfood.Moludi, &sfood.Name); err != nil {
			http.Error(c.Response().Writer, err.Error(), http.StatusInternalServerError)
			return err
		}
		foodname := sfood.Name

		// Execute the second query
		row, err := db.Query("SELECT calories, tan, prot, prov, vita FROM foods WHERE food_name like '%" + foodname + "%'")
		if err != nil {
			http.Error(c.Response().Writer, err.Error(), http.StatusInternalServerError)
			return err
		}
		defer row.Close()

		// Process the second query result
		if row.Next() {
			if err := row.Scan(&sfood.Calories, &sfood.Carbohydrate, &sfood.Protein, &sfood.Province, &sfood.Vitamin); err != nil {
				http.Error(c.Response().Writer, err.Error(), http.StatusInternalServerError)
				return err
			}
		}

		sFodds = append(sFodds, sfood)
	}

	// Encode the result as JSON and return
	c.Response().Writer.Header().Set("Content-Type", "application/json")
	if err := json.NewEncoder(c.Response().Writer).Encode(sFodds); err != nil {
		http.Error(c.Response().Writer, err.Error(), http.StatusInternalServerError)
		return err
	}

	return nil
}
