const express = require('express');
const config = require("./config");
const logger = require("./logger");
//const { errorConverter, errorHandler } = require('./middlewares/error');


const app = express();
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// app.use(errorConverter);
// app.use(errorHandler);
app.listen(config.port, () => {
    logger.info(`Listening to port ${config.port}`);
});
module.exports = app;