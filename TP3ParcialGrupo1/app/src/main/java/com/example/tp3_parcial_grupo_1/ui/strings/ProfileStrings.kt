package com.example.tp3_parcial_grupo_1.ui.strings

// Textos de Perfil. Se mantienen los del diseño de Figma.
object ProfileStrings {
    const val SettingsButton = "Configuración"
    const val EditProfile = "Edit profile"
    const val MySnapshot = "My Snapshot"
    const val Goal = "Goal"
    const val Experience = "Experience"
    const val Training = "Training"
    const val PreferredWorkout = "Preferred Workout"
    const val PersonalisationTitle = "VORA Personalisation"
    const val PersonalisationHeadline = "VORA knows your goals"
    const val PersonalisationBody = "Your coaching adapts to your training, nutrition and activity preferences."
    const val ReviewPreferences = "Review preferences"
    const val YourGoals = "Your Goals"
    const val YourGoalsSubtitle = "Targets and focus areas"
    const val YourPreferences = "Your Preferences"
    const val YourPreferencesSubtitle = "Training, nutrition and coaching"
    const val ConnectedData = "Connected Data"
    const val ConnectedDataSubtitle = "Health & activity data"
    const val Synced = "Synced"
    const val Account = "Account"
    const val AccountSubtitle = "Subscription, notifications & privacy"

    const val EditProfileTitle = "Edit Profile"
    const val ChangePhoto = "Change photo"
    const val FirstName = "First Name"
    const val LastName = "Last Name"
    const val DateOfBirth = "Date of Birth"
    const val Height = "Height"
    const val Weight = "Weight"
    const val Gender = "Gender"
    const val GenderHint = "Used to personalise your recommendations."
    const val SaveChanges = "Save changes"
    const val EditProfileFooter = "Your information is used to personalise your VORA experience."
    const val UnitCm = "cm"
    const val UnitKg = "kg"

    val GenderOptions = listOf("Female", "Male", "Non-binary", "Prefer not to say")

    const val FirstNameRequired = "Ingresá tu nombre."
    const val LastNameRequired = "Ingresá tu apellido."
    const val DateOfBirthRequired = "Ingresá tu fecha de nacimiento."
    const val DateOfBirthInvalid = "Usá el formato 14 March 1995."
    const val HeightInvalid = "La altura debe estar entre 100 y 250 cm."
    const val WeightInvalid = "El peso debe estar entre 30 y 300 kg."

    const val GoalsTitle = "Goals & Preferences"
    const val GoalsHeading = "Your goals"
    const val GoalsIntro = "Tell VORA what you're working towards. Your coaching adapts around this."
    const val PrimaryGoalLabel = "Primary Goal"
    const val PrimaryGoalPrompt = "What's your main goal?"
    const val TrainingFrequencyLabel = "Training Frequency"
    const val WorkoutPreferenceLabel = "Workout Preference"
    const val CoachingStyleLabel = "Coaching Style"
    const val CoachingStylePrompt = "How should Vora Coach you?"
    const val SavePreferences = "Save preferences"

    const val NutritionTitle = "Nutrition Preferences"
    const val NutritionHeading = "Nutrition preferences"
    const val NutritionIntro = "Help VORA make food recommendations that actually fit your lifestyle."
    const val DietaryPreferenceLabel = "Dietary Preference"
    const val FoodsEnjoyLabel = "Foods You Enjoy"
    const val FoodsAvoidLabel = "Foods to Avoid"
    const val AddIngredientPlaceholder = "Add an ingredient..."
    const val AddIngredient = "Agregar ingrediente"
    const val RemoveIngredient = "Quitar"
    const val MealPreferencesLabel = "Meal Preferences"
    const val MealsPerDayLabel = "Meals per day"
    const val PreferredBreakfastLabel = "Preferred breakfast"
    const val PreferredDinnerLabel = "Preferred dinner"
    const val NutritionPriorityPrompt = "What matters most to you?"
    const val IngredientDuplicate = "Ese ingrediente ya está en la lista."

    val FoodsEnjoyOptions = listOf("Chicken", "Eggs", "Fish", "Rice", "Pasta", "Greek yoghurt", "Vegetables", "Fruit")
    val MealsPerDayOptions = listOf("3 meals", "3 meals + 1 snack", "4 meals", "5 meals")
    val BreakfastOptions = listOf("Quick & high protein", "Light & fresh", "Hearty & filling", "I usually skip it")
    val DinnerOptions = listOf("Light & early", "Balanced plate", "High protein", "Hearty & filling")

    fun daysPerWeek(days: Int) = "$days days/week"
}
