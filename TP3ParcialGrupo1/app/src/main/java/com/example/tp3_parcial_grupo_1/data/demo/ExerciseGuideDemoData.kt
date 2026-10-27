package com.example.tp3_parcial_grupo_1.data.demo

// Guías de ejercicio de demostración (textos de Figma): se usan cuando la API no trae instrucciones del ejercicio.
data class ExerciseGuideData(
    val title: String,
    val target: String,
    val steps: List<String>,
    val proCues: String,
    val voraPrompt: String
)

fun exerciseGuideFor(exerciseId: String): ExerciseGuideData {
    return when (exerciseId) {
        "e01" -> ExerciseGuideData(
            title = "Bench Press",
            target = "Target: Chest, Front Delts, Triceps",
            steps = listOf(
                "1. Lie flat on the bench with feet firmly planted.",
                "2. Grip the barbell slightly wider than shoulder-width.",
                "3. Lower the bar smoothly to your mid-chest.",
                "4. Press the weight upward explosively without locking elbows.",
                "5. Control the weight on the way down."
            ),
            proCues = "• Retract your shoulder blades into the bench.\n• Keep a slight arch in your lower back.",
            voraPrompt = "Ask Vora: \"How to fix shoulder pain on bench?\""
        )
        "e03" -> ExerciseGuideData(
            title = "Shoulder Press",
            target = "Target: Shoulders, Upper Chest, Triceps",
            steps = listOf(
                "1. Sit or stand with dumbbells at shoulder height.",
                "2. Brace your core and keep your back straight.",
                "3. Press the dumbbells overhead until arms are extended.",
                "4. Pause briefly at peak contraction.",
                "5. Lower dumbbells with control back to shoulder height."
            ),
            proCues = "• Avoid flaring elbows out excessively.\n• Keep your ribs down and avoid leaning backward.",
            voraPrompt = "Ask Vora: \"Dumbbell vs barbell shoulder press?\""
        )
        "e04" -> ExerciseGuideData(
            title = "Dumbbell Lateral Raise",
            target = "Target: Lateral Delts, Shoulders",
            steps = listOf(
                "1. Stand tall with dumbbells at your sides.",
                "2. Keep a slight bend in your elbows throughout.",
                "3. Raise your arms out to the sides until parallel with floor.",
                "4. Pause briefly at the top.",
                "5. Lower the dumbbells slowly to starting position."
            ),
            proCues = "• Lead with your elbows rather than wrists.\n• Don't use momentum or swing your body.",
            voraPrompt = "Ask Vora: \"Best cues for side delt activation?\""
        )
        "e05" -> ExerciseGuideData(
            title = "Tricep Pushdown",
            target = "Target: Triceps, Arms",
            steps = listOf(
                "1. Attach a bar or rope to a high cable pulley.",
                "2. Keep elbows tucked closely to your sides.",
                "3. Push the cable down until your arms are fully extended.",
                "4. Squeeze your triceps at the bottom position.",
                "5. Return the handle slowly to chest level."
            ),
            proCues = "• Keep upper arms stationary throughout the movement.\n• Focus on full elbow extension at the bottom.",
            voraPrompt = "Ask Vora: \"Rope vs straight bar pushdown?\""
        )
        else -> ExerciseGuideData(
            title = "Seated Cable Row",
            target = "Target: Lats, Mid-Back, Rhomboids",
            steps = listOf(
                "1. Sit upright with your chest lifted.",
                "2. Pull the handle toward your lower torso.",
                "3. Keep your elbows tucked close to your ribs.",
                "4. Pause briefly during peak contraction.",
                "5. Return the weight slowly to start."
            ),
            proCues = "• Keep your shoulders rolled down and back.\n• Do not lean backward to swing the load.",
            voraPrompt = "Ask Vora: \"Explain grip variations\""
        )
    }
}
