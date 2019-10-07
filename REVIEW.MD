**Code review**

*Also available [here](https://github.com/wallaby/code-review-task/pull/1/files) if we manage to share github accounts.*

- [] Structure of the project is flat, it would be nice to have packages to organise files
- [] TasksListAdapter.kt line 33: This might be of personal preference, but I prefer one return point, and this could be converted to a when expression like so:
```kotlin
return when (type) {
            TaskType.general -> ContextCompat.getDrawable(context, R.mipmap.general)
            TaskType.hydration -> ContextCompat.getDrawable(context, R.mipmap.hydration)
            TaskType.medication -> ContextCompat.getDrawable(context, R.mipmap.medication)
            TaskType.nutrition -> ContextCompat.getDrawable(context, R.mipmap.nutrition)
        }
```
- [] TasksListFragment.kt line 25: This is too early to update the progressbar, it needs to be moved to onViewCreated at least to be able to get hold of the view, potentially to onResume or onStart.
- [] TasksListFragment.kt line 32: Cast isn't necessary here.
- [] TasksListFragment.kt line 36: The onSaveInstanceState function is missing, KEY_LAYOUT_MANAGER needs to be set.
- [] TasksListFragment.kt line 41: This is executed on the UI thread. Good job waiting for 2 seconds with the fake network implementation, otherwise it would have been harder to notice.
- [] TasksListFragment.kt line 68: endRefreshing should be called when the network call is finished. Since the network call is done on the UI thread, at the moment this will never show. We could do this on LiveData onChanged, or RxJava onSuccess, or AsyncTask onPostExecute.

