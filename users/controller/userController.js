const User = require('../models/users');

const postUserCalorie = async (req, res) => {
    const userId = req.params.id;
    const { calorie } = req.body;

    try {
        const [user] = await User.getUser(userId);
        const prevcalorieTotal = user[0].calorieTotal;

        if (!user || !prevcalorieTotal) {
            return res.status(404).json({ error: 'User not found' });
          }

        const calorieTotal = calorie;
        const currentDate = new Date();
        const createdAt = currentDate.toISOString().slice(0, 19).replace('T', ' ');
        await User.postDataUser(calorie, calorieTotal, userId, createdAt)
    
        res.status(201).json({
            message: 'success',
            data: {
                id: userId,
                total_calorie: calorie
            }
        });
    } catch (error) {
        res.status(500).json({
            error: 'failed to post data',
            serverMessage: error
        });
    }
}

const getUserCalorie = async (req, res) => {
    const userId = req.params.id;

    try {
        const [user] = await User.getUser(userId);

      if (!user) {
        return res.status(404).json({ error: 'User not found' });
      }

      const calorieTotal = user[0].calorieTotal;
      const lastAddCalorie = user[0].lastAddCalorie;

      res.status(200).json({
        message: 'success',
        data: {
            id: userId,
            total_calorie: calorieTotal,
            lastAddCalorie: lastAddCalorie
        }
      });
    } catch (error) {
        res.status(500).json({
            error: 'failed to get user',
            serverMessage: error
        });
    }
}

const updateUserCalorie = async (req, res) => {
    const userId = req.params.id;
    const { calorie } = req.body;

    try {
        const [user] = await User.getUser(userId);

        if (!user) {
            return res.status(404).json({ error: 'User not found' });
          }
          
        const prevCalorieTotal = user[0].calorieTotal;

        const calorieTotal = prevCalorieTotal + calorie;
        const currentDate = new Date();
        const createdAt = currentDate.toISOString().slice(0, 19).replace('T', ' ');
        await User.updateDataUser(calorie, calorieTotal, userId, createdAt);

        res.status(201).json({
            message: 'success',
            data: {
                id: userId,
                adding_calorie: calorie,
                total_calorie: calorieTotal
            }
        });
    } catch (error) {
        res.status(500).json({
            error: 'failed to update data user',
            serverMessage: error
        });
    }
}

module.exports = { getUserCalorie, updateUserCalorie, postUserCalorie };