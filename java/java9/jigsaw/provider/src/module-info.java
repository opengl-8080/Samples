module sample_module.provider {
    exports sample.provider.api;
    provides sample.provider.api.Foo with sample.provider.impl.FooImpl;
}