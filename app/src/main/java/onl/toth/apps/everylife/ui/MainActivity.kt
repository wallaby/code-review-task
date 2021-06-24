package onl.toth.apps.everylife.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import onl.toth.apps.everylife.R
import onl.toth.apps.everylife.ui.tasks.TaskListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragment_content, TaskListFragment())
                commit()
            }
        }
    }
}
