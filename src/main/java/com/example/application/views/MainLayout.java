package com.example.application.views;

import com.example.application.views.account.AccountView;
import com.example.application.views.homepage.HomeView;
import com.example.application.views.plan.PlanView;
import com.example.application.views.settings.SettingsView;

import com.example.application.views.workout.WorkoutView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.Locale;

/**
 * The main layout is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
public class MainLayout extends VerticalLayout implements RouterLayout, BeforeEnterObserver {

    private Div content;
    private HasElement contentView;
    private Header header = new Header();
    private H1 appName = new H1();
    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, Component icon, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames(Display.FLEX, Gap.XSMALL, Height.MEDIUM, AlignItems.CENTER, Padding.Horizontal.SMALL,
                    TextColor.BODY);
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames(FontWeight.MEDIUM, FontSize.MEDIUM, Whitespace.NOWRAP);

            if (icon != null) {
                link.add(icon);
            }
            link.add(text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }
    }

    public MainLayout() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setMargin(false);

        createHeaderContent();
        this.header.setWidthFull();

        this.content = new Div();
        this.content.setSizeFull();
        this.content.setWidthFull();
        this.content.addClassName("main-content");

        Header navbar = (Header) createFooterContent();
        navbar.setWidthFull();
        add(this.header, this.content, navbar);

        expand(this.content);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Class<?> targetView = event.getNavigationTarget();

        // First try to get the PageTitle annotation
        String currentPageTitle = null;
        PageTitle titleAnnotation = targetView.getAnnotation(PageTitle.class);
        if (titleAnnotation != null) {
            currentPageTitle = titleAnnotation.value();
        }

        // If no PageTitle annotation exists, use the class name
        if (currentPageTitle == null || currentPageTitle.isEmpty()) {
            currentPageTitle = targetView.getSimpleName().replace("View", "");
        }

        // Update the H1 text
        appName.setText(currentPageTitle);
    }

    private void createHeaderContent() {
        this.header = new Header();
        this.header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.ROW, Width.FULL, JustifyContent.BETWEEN);

        appName.addClassNames(Margin.Vertical.MEDIUM, Margin.End.AUTO, FontSize.LARGE);

        var tabsLanguageContainer = new Div();

        Tabs tabsLanguage = new Tabs();

        Tab de = new Tab("Deutsch");
        Tab en = new Tab("Englisch");

        de.setId(Locale.GERMAN.toLanguageTag());
        en.setId(Locale.ENGLISH.toLanguageTag());

        tabsLanguage.add(de, en);

        tabsLanguage.addSelectedChangeListener(e -> {
            var tabsId = e.getSelectedTab().getId().orElse(Locale.ENGLISH.toLanguageTag());
            var language = getTabsLocale(tabsId);
            switchLanguage(language);
        });

        tabsLanguageContainer.add(this.appName, tabsLanguage);
        tabsLanguageContainer.addClassName("tabs-language-container");
        tabsLanguageContainer.addClassNames(Display.FLEX, AlignItems.CENTER);

        Image logo = new Image("images/bunny-logo.png", "Bunny Logo");
        logo.setHeight("4rem");

        this.header.add(logo, tabsLanguageContainer);
        this.header.addClassName("header");
    }

    private Component createFooterContent() {
        Header header = new Header();
        header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);

        Nav nav = new Nav();
        nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Horizontal.MEDIUM, Padding.Vertical.XSMALL);

        UnorderedList list = new UnorderedList();
        list.addClassNames(Display.FLEX, Gap.SMALL, ListStyleType.NONE, Margin.NONE, Padding.NONE, Width.FULL, "navbar__list");
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            list.add(menuItem);
        }

        header.add(nav);
        header.addClassNames("header", ZIndex.XSMALL);

        return header;
    }

    private MenuItemInfo[] createMenuItems() {
        StreamResource iconResource = new StreamResource("barbell.svg",
                () -> getClass().getResourceAsStream("/META-INF/resources/icons/barbell.svg"));

        var homeIcon = LineAwesomeIcon.HOME_SOLID.create();
        var workoutIcon = new SvgIcon(iconResource);
        var planIcon = LineAwesomeIcon.LIST_UL_SOLID.create();
        var settingsIcon = LineAwesomeIcon.COG_SOLID.create();
        var accountIcon = LineAwesomeIcon.USER.create();

        homeIcon.addClassNames("navbar__icon");
        workoutIcon.addClassNames("navbar__icon");
        planIcon.addClassNames("navbar__icon");
        settingsIcon.addClassNames("navbar__icon");
        accountIcon.addClassNames("navbar__icon");

        return new MenuItemInfo[] {
                new MenuItemInfo("Home", homeIcon, HomeView.class),
                new MenuItemInfo("Workout", workoutIcon, WorkoutView.class),
                new MenuItemInfo("Plan", planIcon, PlanView.class),
                new MenuItemInfo("Settings", settingsIcon, SettingsView.class),
                new MenuItemInfo("Account", accountIcon, AccountView.class),
        };
    }

    private Locale getTabsLocale(String lang) {
        return Locale.forLanguageTag(lang);
    }

    public void switchLanguage(Locale locale) {
        // Set the new locale
        getUI().ifPresent(ui -> {
            ui.setLocale(locale);
            // Refresh the UI components to reflect the new locale
            ui.access(this::updateTexts);
        });
    }

    private void updateTexts() {
        // Update other components as needed
        if (this.contentView == null) {
            return;
        }

        if (this.contentView instanceof HomeView) {
            ((HomeView) this.contentView).updateTexts();
        }
        if (this.contentView instanceof PlanView) {
            ((PlanView) this.contentView).updateTexts();
        }
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.contentView = content;
        var className = content.getClass().getName().split("\\.");

        RouterLayout.super.showRouterLayoutContent(content);
        this.content.removeAll();
        this.content.getElement().appendChild(content.getElement());
        this.content.addClassNames(className[className.length - 1]);
    }
}
