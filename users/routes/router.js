const { Router } = require("express");
const userController = require("../controller/userController");

const router = Router();

router.get("/users/:id", userController.getUserCalorie);
router.post("/users/:id", userController.postUserCalorie);
router.put("/users/:id", userController.updateUserCalorie);

module.exports = router;

/**
 * @swagger
 * tags:
 *   name: Users
 *   description: API endpoints for managing user calorie data
 */

/**
 * @swagger
 * /users/{id}:
 *   get:
 *     summary: Get user calorie data
 *     tags: [Users]
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: string
 *         required: true
 *         description: User ID
 *     responses:
 *       200:
 *         description: Success
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/UserCalorieResponse'
 *       404:
 *         description: User not found
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 *       500:
 *         description: Failed to get user
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */

/**
 * @swagger
 * /users/{id}:
 *   post:
 *     summary: Add user calorie data
 *     tags: [Users]
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: string
 *         required: true
 *         description: User ID
 *       - in: body
 *         name: body
 *         description: Calorie data
 *         required: true
 *         schema:
 *           type: object
 *           properties:
 *             calorie:
 *               type: number
 *         example:
 *           calorie: 1500
 *     responses:
 *       201:
 *         description: Success
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/UserCalorieResponse'
 *       404:
 *         description: User not found
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 *       500:
 *         description: Failed to post data
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */

/**
 * @swagger
 * /users/{id}:
 *   put:
 *     summary: Update user calorie data
 *     tags: [Users]
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: string
 *         required: true
 *         description: User ID
 *       - in: body
 *         name: body
 *         description: Calorie data
 *         required: true
 *         schema:
 *           type: object
 *           properties:
 *             calorie:
 *               type: number
 *         example:
 *           calorie: 500
 *     responses:
 *       201:
 *         description: Success
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/UserCalorieResponse'
 *       404:
 *         description: User not found
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 *       500:
 *         description: Failed to update data user
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */

/**
 * @swagger
 * components:
 *   schemas:
 *     UserCalorieResponse:
 *       type: object
 *       properties:
 *         message:
 *           type: string
 *         data:
 *           type: object
 *           properties:
 *             id:
 *               type: string
 *             total_calorie:
 *               type: number
 *             lastAddCalorie:
 *               type: number
 *       example:
 *         message: success
 *         data:
 *           id: "123456"
 *           total_calorie: 2000
 *           lastAddCalorie: 500
 *     ErrorResponse:
 *       type: object
 *       properties:
 *         error:
 *           type: string
 *         serverMessage:
 *           type: string
 *       example:
 *         error: failed to get user
 *         serverMessage: Internal server error
 */
