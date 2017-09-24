module sample_module.user {
    requires sample_module.provider;
    uses sample.provider.api.Foo;
}