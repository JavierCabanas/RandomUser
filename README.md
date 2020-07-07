# Random User - Code Challenge for Aplazame Lead Android Engineer
![CircleCI](https://circleci.com/gh/JavierCabanas/RandomUser/tree/master.svg?style=svg&circle-token=b04352c186322e57e66b79b73f99f7c31719e949)

App that shows a List and a detail of users retrieved from https://my-json-server.typicode.com/Karumi/codetestdata/users

Users can be deleted tapping on the buttons in the list view

### Architecture
![](/art/architecture.svg)
### Implementation details

##### Model–view–viewmodel
MVVM allows us, making use of LiveData, to have a kind of unidirectional architecture in the UI layer simplifying the management of the different states that can be rendered
In addition, using the ViewModel of the Google architecture components dramatically simplifies the management of the Android lifecycle
#### Interactors and Either
Although with the current functionality the interactors are no more than a CRUD at the time when more complex use cases start to appear the interactors can be easily composed, in part, thanks to the use of the Either data type that allows to chain calls using its map and flatmap functions expelling the actual evaluation of the result to the outermost layers

kotlin provides a class ([Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/)) that fulfills the same functionality as Either, but it was decided not to use it because the error handling in Result is based on exceptions while with Either we can use our sealed class of Failure with which we have a restricted set of possible failures
##### Local datasource
Local Datasource is implemented as a memory storage for time and simplicity.
Once the application meets the minimum specifications, it is easy to replace the current datasource by one implemented with a database (Room, Realm, plain sqlite, etc) to provide a more lasting storage
This is possible because [datasource implementations are hidden under the interfaces that define the datasources contract and the datasources users always depend on the interface and not on the concrete implementation](https://en.wikipedia.org/wiki/Dependency_inversion_principle)

### Possible Improvements

* Adding screenshot tests to improve UI regression prevention
* Add a local datasource implemented as Room database, keeping the current memory storage as a cache
* Add cache invalidation policies (especially in the memory storage to decrease unnecessary RAM consumption)
* Integrate CI with Firebase test labs to execute UI tets remotely
* Improve failure modeling: the current one is horizontal to the whole app. It could be convenient to have different sealed classes of failures, more specific to each layer or each functionality.

### Projet management
As a help to make the development of the test in an incremental way [there is a project in Trello](https://trello.com/b/BjNPy0LH/prueba-c%C3%B3digo-aplazame) with a kanban board with the main milestones
