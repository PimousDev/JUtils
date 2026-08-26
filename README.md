# Java Utils - [Project Utils](https://github.com/PimousDev/JavaUtils) [![License: LGPL v3](https://img.shields.io/badge/License-LGPL_v3-orange.svg)](COPYING.LESSER)
_Soon ..._

So, these tools are usually updated when we need it. But, if you have any
suggestion or proposal to upgrade these lasts, don't hesitate to contact us.

> Version: **1.1.0-s.5**

See [Project Utils](https://github.com/PimousDev/JavaUtils).

## Documentation
_Soon ..._

### Developer preparation guide
#### Modules
Add JUtils GitHub Packages maven repository with the following configuration:
```groovy
maven{
	name = "JUtils Github"
	url = "https://maven.pkg.github.com/PimousDev/JUtils"
	credentials{
		username = project.findProperty("gpr.user")
		password = project.findProperty("gpr.key")
	}
}
```

Or use the `jutils.gradle` Gradle script.

#### Build-logic Gradle scripts
Go in `build-logic/src/main/groovy/` and take all the scripts you need.

## License
Java Utils - Project Utils (Java utility modules and libraries)  
Copyright &copy; 2026 - Pimous Dev. (https://www.pimous.dev/)

These modules are free software: you can redistribute them and/or modify them
under the terms of the GNU Lesser General Public License version 3 as published
by the Free Software Foundation.

The latter are distributed in the hope that they will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

You should have received a copy of the GNU General Public License and the GNU
Lesser General Public License along with the modules (Links:
[GNU GPL v3](COPYING) & [GNU LGPL v3](COPYING.LESSER)). If not, see
https://www.gnu.org/licenses/.

## Developers
> Xibitol