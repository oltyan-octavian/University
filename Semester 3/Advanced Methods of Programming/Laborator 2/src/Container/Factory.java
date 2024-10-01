package Container;

import Container.Container;

public interface Factory {
    Container createContainer(ContainerStrategy strategy);
}
