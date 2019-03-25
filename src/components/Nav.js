class BaseNav {
  display() {
    return true;
  }
}

class NavDecorator extends BaseNav {
  constructor(baseNav) {
    super();
    this.baseNav = baseNav;
  }
}

class LoginNav extends NavDecorator {
  constructor(baseNav) {
    super();
    this.baseNav = baseNav;
  }
  display(props) {
    return props.isLoggedIn && this.baseNav.display(props);
  }
}

class ListNav extends NavDecorator {
  constructor(baseNav) {
    super();
    this.baseNav = baseNav;
  }
  display(props) {
    return props.isList && this.baseNav.display(props);
  }
}

class AdminNav extends NavDecorator {
  constructor(baseNav) {
    super();
    this.baseNav = baseNav;
  }
  display(props) {
    return props.isAdmin && this.baseNav.display(props);
  }
}

class DetailNav extends NavDecorator {
  constructor(baseNav) {
    super();
    this.baseNav = baseNav;
  }
  display(props) {
    return props.page === 'info' && this.baseNav.display(props);
  }
}

export {
  BaseNav, LoginNav, ListNav, AdminNav, DetailNav
};
