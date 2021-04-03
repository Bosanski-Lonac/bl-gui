module bl_gui {
	exports app;
	exports gui.komponente;
	exports controller;
	exports gui;
	exports model;
	exports wrapper;

	requires transitive bl_core;
	requires java.sql;
	requires transitive javafx.controls;
		requires javafx.base;
		requires javafx.graphics;
	requires org.controlsfx.controls;
	requires spring.core;
	requires spring.web;
}