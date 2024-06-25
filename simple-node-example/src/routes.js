const express = require('express')
const router = express.Router()
const paths = require('./paths.js')
const greetingController = require('./greeting.controller.js')
const { request } = require("express");
const middleware = require('./middleware.js')

router.get(paths.greeting.path, middleware, greetingController.returnHelloWorld)

router.post(paths.process_greeting.path, middleware, greetingController.postGreeting)
module.exports = router;