const pool = require('../config/db').pool;

const getUser = (user_id) => {
  const query = `SELECT * FROM calories WHERE user_id='${user_id}' ORDER BY createdAt DESC LIMIT 1`;

  return pool.execute(query);
}

const postDataUser = (calorie, calorieTotal, user_id, createdAt) => {
    const query = ` INSERT INTO calories (lastAddCalorie, calorieTotal, user_id, createdAt)
                    VALUES ('${calorie}', '${calorieTotal}', '${user_id}', '${createdAt}')`

    return pool.execute(query);
}

const updateDataUser = (calorie, calorieTotal, user_id, createdAt) => {
    const query = ` INSERT INTO calories (lastAddCalorie, calorieTotal, user_id, createdAt)
                    VALUES ('${calorie}', '${calorieTotal}', '${user_id}', '${createdAt}')`

    return pool.execute(query);
}

module.exports = { getUser, updateDataUser, postDataUser };