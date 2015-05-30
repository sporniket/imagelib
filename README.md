#Sporniket Image Library

Utility libraries to manipulate images and images files.

```
    Sporniket Image Library is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Sporniket Image Library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
```

# How to use in your project with maven

## Build and install the library
To get the latest available code, clone the git repository, build and install to the maven local repository.

```
git clone https://github.com/sporniket/imagelib.git
cd imagelib
(optional, if appliable) git checkout version_to_use
mvn install
```

## Add a dependency to your project
Add the needed dependencies in your pom file :

```
<dependency>
	<groupId>com.sporniket.imagelib</groupId>
	<artifactId>sporniket-imagelib-core</artifactId>
	<version><!-- the version to use --></version>
</dependency>
```
