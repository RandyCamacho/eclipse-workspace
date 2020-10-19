# Login

Java user account application. Uses JavaFX to allow user signup and login via GUI.

## Getting Started

The following instructions will get Login up and running on your local machine for demonstration and testing purposes.

### Prerequisites

JUnit 4 must be included in the build path. In order to properly import into Eclipse:

```
Select **File** > **Import**. From the dropdown menu, select: **General** > **Projects from Folder or Archive**.
```

You should now be able to select the repository as your import source and finishing the wizard prompts you to add JUnit to the build path.

### Installing

From the working directory, production source can be compiled with: "javac src/login/Welcome.java". Then, "java src/login/Welcome".
Alternatively, run the project as an application in eclipse.

## Running the tests

Unit tests are provided in src/tests. Running each class as a JUnit Test in eclipse provides the most insight.

### UserAccountTest

Runs a single test to test the creation of a new account.

### UserAccountManagerTest

Runs 2 tests:

* Check to see if an existing account can be found in the manager.
* Check to see that an unregistered account does not return a false positive.

## Contributing

If you are unfamiliar with git (command line tool), download [Github Desktop Application](https://desktop.github.com/).

```
Login to your github account in the application. Click File > Clone. If you do not see the "youngmamba8/CS-271" repository, accept your [invitation](https://github.com/youngmamba8/CS-271/invitations).
Clone the repository into your eclipse-workspace directory.
```

You should now be able to edit the source code in the src folder after importing to eclipse (see installing).
When you make changes, commit with a comment. Then press the "push" button.

## Authors

* **Toby Bower** - *Initial work* [lukeblood](https://github.com/lukeblood)
* **Randy Camacho** - *Production classes* [youngmamba8](https://github.com/youngmamba8)
* **Brian Morck**
* **Quinn Shultz** - *Test classes and documentation* [quinnshultz](https://github.com/quinnshultz)
* **Connor Wood**

## Acknowledgments

* Provided starter files
