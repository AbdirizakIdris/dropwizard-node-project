function middleware(req, res, next) {
  console.log(`Accessed: [${new Date().toLocaleString()}] ${req.method} ${req.url}`);
  next();
}

module.exports = middleware;