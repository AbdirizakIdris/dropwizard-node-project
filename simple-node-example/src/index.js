const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const router = require('./routes')
app.use(bodyParser.json());


app.use('/', router)

app.listen(3000, () => {
  console.log('Server running on port 3000');
});