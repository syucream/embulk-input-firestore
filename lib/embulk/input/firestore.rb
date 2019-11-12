Embulk::JavaPlugin.register_input(
  "firestore", "org.embulk.input.firestore.FirestoreInputPlugin",
  File.expand_path('../../../../classpath', __FILE__))
