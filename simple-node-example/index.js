const express = require('express');
const app = express();

app.get('/', (req, res) => {
  const response = {
    text: 'hello world'
  };
  res.json(response);
});

app.listen(3000, () => {
  console.log('Server running on port 3000');
});
