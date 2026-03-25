module dev.pimous.pu.jutils.app{
	requires transitive java.logging;

	requires transitive dev.pimous.pu.jutils.base;
	requires transitive dev.pimous.pu.jutils.config;
	requires transitive dev.pimous.pu.jutils.i18n;
	requires transitive dev.pimous.pu.jutils.logger;

	exports dev.pimous.pu.jutils.app;
	exports dev.pimous.pu.jutils.app.util;
}