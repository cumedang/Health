
package main

import (
	"Health/Router"
	"github.com/labstack/echo"
)

func main() {
	e := echo.New()
	e.Static("/sans", "frontend")
	e.GET("/", Router.IndexHandler)
	e.GET("/login", Router.LoginHanddler)
	e.GET("/sign", Router.SignHanddler)
	e.POST("/login", Router.LoginProcees)
	e.POST("/sign", Router.SignProcess)
	e.GET("/diet", Router.DietHanddler)
	e.Logger.Fatal(e.Start(":8081"))
}

package main

import (
	"Health/Router"
	"github.com/labstack/echo"
)

func main() {
	e := echo.New()
	e.Static("/sans", "frontend")
	e.GET("/", Router.IndexHandler)
	e.GET("/login", Router.LoginHanddler)
	e.GET("/sign", Router.SignHanddler)
	e.POST("/login", Router.LoginProcees)
	e.POST("/sign", Router.SignProcess)
	e.Logger.Fatal(e.Start(":8081"))
}

