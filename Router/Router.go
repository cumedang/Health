package Router

import (
	"Health/utill"
	"database/sql"
	"net/http"

	_ "github.com/go-sql-driver/mysql"
	"github.com/gorilla/sessions"
	"github.com/labstack/echo"
	"golang.org/x/crypto/bcrypt"
)

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
	db, err := sql.Open("mysql", "iana:12923@tcp(127.0.0.1:3306)/adoins")
	defer db.Close()
	utill.Error(err)
	var id string
	var password []byte
	var name string

	err = db.QueryRow("SELECT id, pw, name from member where id = ?", fid).Scan(&id, &password, &name)
	utill.Error(err)
	if err != nil {
		return c.JSON(http.StatusUnauthorized, map[string]interface{}{"success": false, "message": "Login failed. Incorrect username or password."})
	}
	err = bcrypt.CompareHashAndPassword(password, []byte(fpassword))
	if err == nil {
		session.Values["name"] = name
		err = session.Save(c.Request(), c.Response())
		utill.Error(err)
		session.Save(c.Request(), c.Response())
		//fmt.Println(session.Values["name"]) 세션 이름 가져오기
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

	db, err := sql.Open("mysql", "iana:12923@tcp(127.0.0.1:3306)/adoins")
	utill.Error(err)
	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(fpassword), bcrypt.DefaultCost)
	utill.Error(err)
	db.Query("INSERT INTO member values(?,?,?)", fid, hashedPassword, fname)
	defer db.Close()

	return c.JSON(http.StatusUnauthorized, map[string]interface{}{"success": true, "message": "Success"})
}

func DietHanddler(c echo.Context) error {
	return c.File("frontend/moludi.html")
}
