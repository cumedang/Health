package utill

import "log"

func Error(err error) {
	if err != nil {
		log.Panic(err)
	}
}
