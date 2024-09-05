module.exports = {
  returnHelloWorld (req, res) {
    const response = {
      text: 'Hello, world!'
    };
    res.json(response)
  },

  postGreeting(req, res) {
    const name = req.body.name
    const capitalisedName = name.toUpperCase()
    const response = {
      name: capitalisedName
    };
    res.json(response);
  }
}